package org.liu.core.service.seckill.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liu.core.entity.seckill.SeckillStock;
import org.liu.core.mapper.seckill.SeckillStockMapper;
import org.liu.core.service.seckill.SeckillStockDataService;
import org.springframework.stereotype.Service;

/**
 * 秒杀库存数据Service实现（仅调用Mapper）
 */
@Service
public class SeckillStockDataServiceImpl extends ServiceImpl<SeckillStockMapper, SeckillStock>
        implements SeckillStockDataService {

    @Override
    public int decrStockByActivityId(Long activityId) {
        return baseMapper.decrStockByActivityId(activityId);
    }
}