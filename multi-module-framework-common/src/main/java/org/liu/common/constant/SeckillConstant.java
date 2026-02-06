package org.liu.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 秒杀通用常量（从Nacos配置中心读取，无业务逻辑）
 */
@Component
public class SeckillConstant {
    // Redis库存Key前缀（Nacos配置）
    public static String SECKILL_STOCK_KEY;
    // Redis用户订单防重Key前缀（Nacos配置）
    public static String SECKILL_USER_ORDER_KEY;
    // 秒杀订单号前缀（Nacos配置）
    public static String SECKILL_ORDER_NO_PREFIX;
    // 默认接口限流次数/分钟（Nacos配置）
    public static Integer DEFAULT_LIMIT_COUNT;

    @Value("${seckill.redis.key-prefix.stock}")
    public void setSeckillStockKey(String seckillStockKey) {
        SECKILL_STOCK_KEY = seckillStockKey;
    }

    @Value("${seckill.redis.key-prefix.user-order}")
    public void setSeckillUserOrderKey(String seckillUserOrderKey) {
        SECKILL_USER_ORDER_KEY = seckillUserOrderKey;
    }

    @Value("${seckill.redis.order-no-prefix}")
    public void setSeckillOrderNoPrefix(String seckillOrderNoPrefix) {
        SECKILL_ORDER_NO_PREFIX = seckillOrderNoPrefix;
    }

    @Value("${seckill.limit.count}")
    public void setDefaultLimitCount(Integer defaultLimitCount) {
        DEFAULT_LIMIT_COUNT = defaultLimitCount;
    }
}