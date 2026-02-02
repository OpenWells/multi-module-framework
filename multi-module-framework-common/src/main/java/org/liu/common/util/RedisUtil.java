package org.liu.common.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类（适配JDK17，封装常用操作）
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    // ==================== 普通操作 ====================
    /**
     * 设置缓存（带过期时间）
     */
    public <T extends Serializable> void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置缓存（永久）
     */
    public <T extends Serializable> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取缓存
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断缓存是否存在
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // ==================== 分布式锁 ====================
    /**
     * 获取分布式锁（JDK17 Lambda简化脚本）
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        String script = """
                if redis.call('setNx', KEYS[1], ARGV[1]) then
                    if redis.call('expire', KEYS[1], ARGV[2]) then
                        return 1
                    else
                        return 0
                    end
                else
                    return 0
                end
                """; // JDK17文本块语法
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId, expireTime);
        return result != null && result == 1;
    }

    /**
     * 释放分布式锁
     */
    public boolean releaseLock(String lockKey, String requestId) {
        String script = """
                if redis.call('get', KEYS[1]) == ARGV[1] then
                    return redis.call('del', KEYS[1])
                else
                    return 0
                end
                """;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        return result != null && result == 1;
    }
}