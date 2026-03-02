package org.liu.core.entity.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀库存Entity（对应表：seckill_stock，与活动一对一关联）
 */
@Data
@TableName("seckill_stock")
public class SeckillStock {
    @TableId(type = IdType.AUTO)
    private Long id;          // 库存ID（主键）
    private Long activityId;  // 关联活动ID（唯一）
    private Integer stockNum; // 剩余库存数
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}