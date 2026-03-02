package org.liu.core.service.seckill.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liu.core.entity.seckill.SeckillOrder;
import org.liu.core.mapper.seckill.SeckillOrderMapper;
import org.liu.core.service.seckill.SeckillOrderDataService;
import org.springframework.stereotype.Service;

/**
 * 秒杀订单数据Service实现（仅调用Mapper）
 */
@Service
public class SeckillOrderDataServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder>
        implements SeckillOrderDataService {

    @Override
    public SeckillOrder getByUserIdAndActivityId(Long userId, Long activityId) {
        return baseMapper.selectByUserIdAndActivityId(userId, activityId);
    }
}