package org.liu.biz.service.seckill.redisLua;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liu.api.dto.seckill.SeckillDTO;
import org.liu.biz.service.seckill.SeckillBizService;
import org.liu.biz.util.SeckillBizUtil;
import org.liu.core.entity.seckill.SeckillOrder;
import org.liu.core.entity.seckill.SeckillStock;
import org.liu.core.mapper.seckill.SeckillOrderMapper;
import org.liu.core.mapper.seckill.SeckillStockMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.liu.biz.exception.SeckillBizException;
import org.liu.biz.service.SeckillStockWarmUpService;
import org.liu.common.config.SeckillRedisConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Nacos多模块版：库存同步+秒杀核心逻辑
 * 核心：Redis扣减 → 异步更新DB → 差异回补 → 库存对账
 */
@Slf4j
@Service("redisLuaSeckillBizService")
@RequiredArgsConstructor
public class RedisLuaSeckillBizServiceImpl implements SeckillBizService {

    // 依赖common模块配置
    private final SeckillRedisConfig seckillRedisConfig;
    private final SeckillStockWarmUpService warmUpService;
    // biz模块依赖
    private final StringRedisTemplate stringRedisTemplate;
    private final SeckillStockMapper seckillStockMapper;
    private final SeckillOrderMapper seckillOrderMapper;

    // ========== 核心修改：Lua脚本以静态字符串嵌入，无需外部文件 ==========
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        // 用Java文本块（"""）编写Lua脚本，无需转义，保持原逻辑
        String luaScript = """
                -- 秒杀库存扣减Lua脚本（原子操作，防超卖/防重复秒杀）
                -- KEYS[1]：库存Key（如seckill_stock_1）
                -- KEYS[2]：用户防重Key（如seckill_user_order_1）
                -- ARGV[1]：用户ID
                -- ARGV[2]：秒杀数量
                if redis.call('sismember', KEYS[2], ARGV[1]) == 1 then
                    return 0  -- 0=用户已秒杀
                end
                local stock = tonumber(redis.call('get', KEYS[1]) or "0")
                local num = tonumber(ARGV[2])
                if stock < num then
                    return -1  -- -1=库存不足
                end
                redis.call('decrby', KEYS[1], num)
                redis.call('sadd', KEYS[2], ARGV[1])
                return 1  -- 1=秒杀成功
                """;
        // 设置脚本文本（替代读取外部文件）
        SECKILL_SCRIPT.setScriptText(luaScript);
        // 指定返回值类型（和之前一致）
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    @Override
    public void doSeckill(SeckillDTO dto) {
        // 1. 兜底校验：Redis无库存则查DB并预热（多模块一致性核心）
        String stockKey = seckillRedisConfig.getStockKey(dto.getActivityId());
        String redisStock = stringRedisTemplate.opsForValue().get(stockKey);
        if (redisStock == null || "0".equals(redisStock)) {
            SeckillStock dbStock = seckillStockMapper.selectByActivityId(dto.getActivityId());
            if (dbStock == null || dbStock.getStockNum() <= 0) {
                throw new SeckillBizException("茅台秒杀库存不足");
            }
            // DB有库存，自动预热Redis（保证多模块数据一致）
            warmUpService.warmUpStock(dto.getActivityId());
        }

        // 2. Lua原子扣减Redis库存
        String userKey = seckillRedisConfig.getUserOrderKey(dto.getActivityId());
        List<String> keys = new ArrayList<>();
        keys.add(stockKey);
        keys.add(userKey);
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT, keys, dto.getUserId().toString(), "1"
        );

        // 3. 结果处理
        if (result == 0) throw new SeckillBizException("请勿重复秒杀");
        if (result == -1) throw new SeckillBizException("库存不足");
        if (result != 1) throw new SeckillBizException("秒杀失败");

        // 4. 异步更新DB（多模块：避免阻塞Redis秒杀）
        asyncUpdateDb(dto.getActivityId(), dto.getUserId());
    }

    /**
     * 异步更新DB（库存同步核心）
     */
    @Async // 多模块：启动类加@EnableAsync
    @Transactional(rollbackFor = Exception.class)
    public void asyncUpdateDb(Long activityId, Long userId) {
        try {
            // 4.1 乐观锁扣减DB库存（防超卖）
            int affectRows = seckillStockMapper.decrStockByActivityId(activityId);
            if (affectRows == 0) {
                // DB扣减失败，回补Redis（多模块一致性关键）
                stringRedisTemplate.opsForValue().increment(seckillRedisConfig.getStockKey(activityId), 1);
                log.error("[库存同步失败] 用户{}秒杀成功，DB扣减失败，已回补Redis", userId);
                throw new SeckillBizException("秒杀失败，库存不足");
            }

            // 4.2 生成订单
            SeckillOrder order = new SeckillOrder();
            order.setOrderNo(SeckillBizUtil.generateOrderNo());
            order.setActivityId(activityId);
            order.setGoodsId(10001L);
            order.setUserId(userId);
            order.setStatus(0);
            seckillOrderMapper.insert(order);
            log.info("[库存同步成功] 用户{}秒杀成功，DB库存已扣减", userId);

        } catch (Exception e) {
            log.error("[库存同步异常] 活动{}用户{}", activityId, userId, e);
            throw new SeckillBizException("订单生成失败");
        }
    }

    /**
     * 库存对账（多模块：活动结束后执行，保证Redis/DB一致）
     */
    public void checkStockConsistency(Long activityId) {
        // 1. 查Redis库存
        String stockKey = seckillRedisConfig.getStockKey(activityId);
        Integer redisStock = Integer.parseInt(stringRedisTemplate.opsForValue().get(stockKey) == null ? "0" : stringRedisTemplate.opsForValue().get(stockKey));
        // 2. 查DB库存
        SeckillStock dbStock = seckillStockMapper.selectByActivityId(activityId);
        Integer dbStockNum = dbStock == null ? 0 : dbStock.getStockNum();
        // 3. 差异同步（以DB为准，多模块最终一致性）
        if (!redisStock.equals(dbStockNum)) {
            log.error("[库存对账] 活动{}不一致：Redis={}, DB={}", activityId, redisStock, dbStockNum);
            stringRedisTemplate.opsForValue().set(stockKey, String.valueOf(dbStockNum));
        }
    }
}