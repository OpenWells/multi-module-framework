package org.liu.biz.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 秒杀业务常量（业务规则相关，仅biz层使用，从Nacos读取）
 */
@Component
public class SeckillBizConstant {
    // 秒杀接口限流标识
    public static String SECKILL_API_KEY;
    // 订单待支付超时时间（秒）
    public static Long ORDER_EXPIRE_TIME;

    @Value("${seckill.order.expire-time}")
    public void setOrderExpireTime(Long orderExpireTime) {
        ORDER_EXPIRE_TIME = orderExpireTime;
    }

    static {
        SECKILL_API_KEY = "seckill:api:doSeckill";
    }
}