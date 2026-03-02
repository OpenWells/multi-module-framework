package org.liu.core.service.seckill.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liu.core.entity.seckill.SeckillActivity;
import org.liu.core.mapper.seckill.SeckillActivityMapper;
import org.liu.core.service.seckill.SeckillActivityDataService;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动数据Service实现（仅调用Mapper，纯数据操作）
 */
@Service
public class SeckillActivityDataServiceImpl extends ServiceImpl<SeckillActivityMapper, SeckillActivity>
        implements SeckillActivityDataService {
}