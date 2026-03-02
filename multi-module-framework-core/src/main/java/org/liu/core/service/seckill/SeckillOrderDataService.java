package org.liu.core.service.seckill;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liu.core.entity.seckill.SeckillOrder;

/**
 * 秒杀订单-数据层Service（纯CRUD，无业务逻辑）
 */
public interface SeckillOrderDataService extends IService<SeckillOrder> {
    // 按用户ID+活动ID查询订单（防重）
    SeckillOrder getByUserIdAndActivityId(Long userId, Long activityId);
}