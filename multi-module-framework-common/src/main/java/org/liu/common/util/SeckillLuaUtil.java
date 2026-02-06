package org.liu.common.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 秒杀Lua脚本工具（通用无业务感知，保证秒杀原子性）
 */
@Component
public class SeckillLuaUtil {
    // 秒杀核心Lua脚本：1.校验库存 2.校验用户是否已下单 3.扣库存 4.记录用户下单
    private static final String SECKILL_LUA_SCRIPT = """
            local stockKey = KEYS[1]
            local userOrderKey = KEYS[2]
            local stock = redis.call('get', stockKey)
            local userId = ARGV[1]
            
            -- 库存不足
            if not stock or tonumber(stock) <= 0 then
                return 0
            end
            -- 用户已下单（防重）
            if redis.call('sismember', userOrderKey, userId) == 1 then
                return 2
            end
            -- 扣库存+记录用户
            redis.call('decr', stockKey)
            redis.call('sadd', userOrderKey, userId)
            return 1
            """;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 执行秒杀Lua脚本
     * @param stockKey 库存RedisKey
     * @param userOrderKey 用户防重RedisKey
     * @param userId 用户ID
     * @return 0=库存不足，1=秒杀成功，2=用户已下单
     */
    public Integer executeSeckillLua(String stockKey, String userOrderKey, Long userId) {
        DefaultRedisScript<Integer> script = new DefaultRedisScript<>();
        script.setScriptText(SECKILL_LUA_SCRIPT);
        script.setResultType(Integer.class);
        // 执行Lua脚本（原子操作）
        return redisUtil.executeLua(script, Collections.singletonList(stockKey), userOrderKey, userId);
    }
}