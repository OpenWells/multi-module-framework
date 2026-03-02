package org.liu.core.mapper.seckill;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.liu.core.entity.seckill.SeckillActivity;
import org.springframework.stereotype.Repository;

/**
 * 秒杀活动Mapper（纯数据CRUD，无业务逻辑）
 */
@Repository
public interface SeckillActivityMapper extends BaseMapper<SeckillActivity> {
}