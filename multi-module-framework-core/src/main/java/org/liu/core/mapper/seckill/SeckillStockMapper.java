package org.liu.core.mapper.seckill;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.liu.core.entity.seckill.SeckillStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 秒杀库存Mapper，提供乐观锁扣减库存方法（数据库方案用）
 */
@Repository
public interface SeckillStockMapper extends BaseMapper<SeckillStock> {
    /**
     * 乐观锁扣减库存：update seckill_stock set surplus_stock = surplus_stock -1, version = version +1 where activity_id = ? and surplus_stock >0 and version = ?
     */
    int deductStockByVersion(@Param("activityId") Long activityId, @Param("version") Integer version);
}