package org.liu.common.config;

import com.alibaba.nacos.shaded.com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 单机限流工具类（Guava RateLimiter 令牌桶）
 * 秒杀接口通用限流
 */
@Component
public class SeckillRateLimiter {
    // 存储不同活动的限流实例，key=activityId，value=RateLimiter
    private final ConcurrentHashMap<Long, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * 获取限流实例，默认单机QPS=1000
     */
    public RateLimiter getRateLimiter(Long activityId) {
        return rateLimiterMap.computeIfAbsent(activityId, k -> RateLimiter.create(1000.0));
    }

    /**
     * 尝试获取令牌，获取成功返回true，失败返回false
     */
    public boolean tryAcquire(Long activityId) {
        return getRateLimiter(activityId).tryAcquire();
    }
}