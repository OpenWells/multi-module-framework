package org.liu.common.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通用Redis工具（无业务感知，封装基础操作，原有框架类，复用）
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 设置缓存（带过期时间）
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 设置缓存（无过期时间）
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取缓存
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除缓存
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    // 判断缓存是否存在
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 执行Lua脚本
    public <T> T executeLua(DefaultRedisScript<T> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    // 数值自减
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    // 集合添加元素
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    // 集合判断元素是否存在
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
}