package org.liu.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 秒杀公共枚举（活动/订单状态，所有层可复用）
 */
@Getter
@AllArgsConstructor
public enum SeckillStatusEnum {
    // 秒杀活动状态
    ACTIVITY_NOT_START(0, "未开始"),
    ACTIVITY_ON_GOING(1, "进行中"),
    ACTIVITY_END(2, "已结束"),
    ACTIVITY_OFF_SHELF(3, "已下架"),

    // 秒杀订单状态
    ORDER_WAIT_PAY(0, "待支付"),
    ORDER_PAID(1, "已支付"),
    ORDER_CANCEL(2, "已取消"),
    ORDER_FINISH(3, "已完成");

    // 状态码
    private final Integer code;
    // 状态描述
    private final String desc;
}