package org.liu.common.config;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流工具类（Redis+INCR）
 * 秒杀集群通用限流，默认集群QPS=10000
 */
@Component
public class SeckillRedisLimiter {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 限流key前缀
    private static final String LIMIT_KEY_PREFIX = "seckill:limit:";
    // 默认集群QPS阈值
    private static final long DEFAULT_QPS_LIMIT = 10000;

    /**
     * 尝试获取限流许可
     * @param activityId 秒杀活动ID
     * @return true=成功，false=限流
     */
    public boolean tryAcquire(Long activityId) {
        String key = LIMIT_KEY_PREFIX + activityId;
        // Redis INCR 原子自增
        Long current = stringRedisTemplate.opsForValue().increment(key);
        // 第一次请求，设置过期时间1秒
        if (current == 1) {
            stringRedisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }
        // 超过阈值则限流
        return current <= DEFAULT_QPS_LIMIT;
    }
}