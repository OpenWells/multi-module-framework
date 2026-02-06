package org.liu.core.entity.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("seckill_stock")
public class SeckillStock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long activityId;
    private Integer surplusStock;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}