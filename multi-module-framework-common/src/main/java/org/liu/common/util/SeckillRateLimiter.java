package org.liu.common.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 秒杀本地限流工具（通用无业务感知，原有框架可复用）
 */
@Component
public class SeckillRateLimiter {
    // 存储每个接口的访问次数：key=接口标识，value=原子计数
    private final ConcurrentHashMap<String, AtomicInteger> limitMap = new ConcurrentHashMap<>();

    /**
     * 限流判断
     * @param apiKey 接口标识
     * @param maxCount 最大允许次数/分钟
     * @return true=允许访问，false=限流
     */
    public boolean allowAccess(String apiKey, int maxCount) {
        AtomicInteger count = limitMap.computeIfAbsent(apiKey, k -> new AtomicInteger(0));
        return count.incrementAndGet() <= maxCount;
    }

    /**
     * 重置限流计数（可结合Spring Schedule做定时重置）
     * @param apiKey 接口标识
     */
    public void resetLimit(String apiKey) {
        limitMap.remove(apiKey);
    }
}