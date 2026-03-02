package org.liu.api.vo.seckill;

import lombok.Data;

/**
 * 秒杀出参VO（web层返回响应数据，屏蔽底层Entity）
 */
@Data
public class SeckillVO {
    private Boolean success; // 是否秒杀成功
    private String orderNo;  // 秒杀订单号（成功则返回）
    private String msg;      // 响应信息（失败原因/成功提示）
}