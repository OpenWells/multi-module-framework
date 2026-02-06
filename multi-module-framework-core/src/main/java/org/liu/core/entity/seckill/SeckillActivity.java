package org.liu.core.entity.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀活动表实体
 * 对应表：seckill_activity
 */
@Data
@TableName("seckill_activity")
public class SeckillActivity {
    /**
     * 活动ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称，如：春节茅台秒杀活动
     */
    private String activityName;

    /**
     * 关联商品ID
     */
    private Long goodsId;

    /**
     * 活动总库存（茅台总瓶数）
     */
    private Integer totalStock;

    /**
     * 秒杀开始时间
     */
    private LocalDateTime startTime;

    /**
     * 秒杀结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动状态：0-未开始，1-进行中，2-已结束，3-已下架
     */
    private Integer status;

    /**
     * 创建时间（MySQL默认CURRENT_TIMESTAMP）
     */
    private LocalDateTime createTime;

    /**
     * 更新时间（MySQL默认CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP）
     */
    private LocalDateTime updateTime;
}