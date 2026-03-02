package org.liu.core.service.seckill;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liu.core.entity.seckill.SeckillStock;

/**
 * 秒杀库存-数据层Service（纯CRUD，无业务逻辑）
 */
public interface SeckillStockDataService extends IService<SeckillStock> {
    // 乐观锁扣减库存
    int decrStockByActivityId(Long activityId);
}