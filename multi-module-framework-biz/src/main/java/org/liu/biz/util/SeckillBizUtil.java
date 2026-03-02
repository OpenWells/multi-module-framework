package org.liu.biz.util;

import jakarta.annotation.Resource;
import org.liu.common.constant.SeckillConstant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 秒杀业务工具（仅biz层使用，业务相关）
 */
@Component
public class SeckillBizUtil {
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    // 生成唯一秒杀订单号：前缀 + 时间戳 + 6位随机数
    public static String generateOrderNo() {
        String timeStr = LocalDateTime.now().format(FORMATTER);
        int random = RANDOM.nextInt(900000) + 100000;
        return SeckillConstant.SECKILL_ORDER_NO_PREFIX + timeStr + random;
    }
}