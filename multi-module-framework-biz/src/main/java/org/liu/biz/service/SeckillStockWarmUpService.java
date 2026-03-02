package org.liu.biz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liu.core.entity.seckill.SeckillStock;
import org.liu.core.mapper.seckill.SeckillStockMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.liu.common.config.SeckillRedisConfig;

/**
 * Nacos多模块版：库存预热服务（biz模块，核心业务）
 * 支持：手动预热 + 定时预热（活动前5分钟）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillStockWarmUpService {

    // 依赖common模块的配置类
    private final SeckillRedisConfig seckillRedisConfig;
    private final SeckillStockMapper seckillStockMapper;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 手动预热（管理员接口调用）
     */
    public void warmUpStock(Long activityId) {
        // 1. 从DB查库存（多模块：biz模块操作Mapper）
        SeckillStock stock = seckillStockMapper.selectByActivityId(activityId);
        if (stock == null || stock.getStockNum() <= 0) {
            log.error("[预热失败] 秒杀活动{}无库存", activityId);
            return;
        }

        // 2. 从Nacos配置读取Key前缀，生成Redis Key
        String stockKey = seckillRedisConfig.getStockKey(activityId);
        // 3. 写入Redis（覆盖旧值，保证一致性）
        stringRedisTemplate.opsForValue().set(stockKey, String.valueOf(stock.getStockNum()));
        log.info("[预热成功] 秒杀活动{}，Redis Key={}，库存={}",
                activityId, stockKey, stock.getStockNum());
    }

    /**
     * 定时预热（Nacos多模块：启动类加@EnableScheduling）
     * cron：茅台秒杀8点开始，7:55预热
     */
    @Scheduled(cron = "0 55 7 * * ?")
    public void scheduleWarmUpStock() {
        log.info("[定时预热] 开始执行茅台秒杀库存预热");
        warmUpStock(1L); // 茅台活动ID=1
        log.info("[定时预热] 茅台秒杀库存预热完成");
    }

    /**
     * 批量预热（多活动场景）
     */
    public void batchWarmUpStock() {
        // 多模块可扩展：从DB查所有未开始的秒杀活动，批量预热
        warmUpStock(1L);
    }
}