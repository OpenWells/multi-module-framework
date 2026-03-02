package org.liu.core.entity.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀订单Entity（对应表：seckill_order，含唯一防重索引uk_user_activity）
 */
@Data
@TableName("seckill_order")
public class SeckillOrder {
    @TableId(type = IdType.AUTO)
    private Long id;          // 订单ID（主键）
    private Long activityId;  // 关联活动ID
    private Long goodsId;     // 商品ID
    private Long userId;      // 下单用户ID
    private String orderNo;   // 秒杀订单号（唯一）
    private Integer status;   // 订单状态（对应SeckillStatusEnum）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime payTime;    // 支付时间（可空）
    private LocalDateTime cancelTime; // 取消时间（可空）
}