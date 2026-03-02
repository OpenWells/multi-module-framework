package org.liu.core.mapper.seckill;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.liu.core.entity.seckill.SeckillOrder;
import org.springframework.stereotype.Repository;

/**
 * 秒杀订单Mapper（含自定义防重查询方法）
 */
@Repository
public interface SeckillOrderMapper extends BaseMapper<SeckillOrder> {
    // 按用户ID+活动ID查询订单（防重核心方法）
    SeckillOrder selectByUserIdAndActivityId(Long userId, Long activityId);
}