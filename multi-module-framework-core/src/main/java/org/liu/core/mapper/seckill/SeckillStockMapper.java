package org.liu.core.mapper.seckill;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.liu.core.entity.seckill.SeckillStock;
import org.springframework.stereotype.Repository;

/**
 * 秒杀库存Mapper（含乐观锁扣库存方法）
 */
@Repository
public interface SeckillStockMapper extends BaseMapper<SeckillStock> {
    /**
     * 根据活动ID查询库存（缓存预热/兜底校验核心方法）
     * @param activityId 秒杀活动ID（茅台秒杀=1）
     * @return 库存实体（含库存数量），无数据返回null
     */
    SeckillStock selectByActivityId(@Param("activityId") Long activityId);

    /**
     * 乐观锁扣减库存（秒杀成功后异步更新DB核心方法）
     * @param activityId 秒杀活动ID
     * @return 影响行数：1=扣减成功，0=扣减失败（库存不足）
     */
    int decrStockByActivityId(@Param("activityId") Long activityId);

    /**
     * 新增库存（初始化活动库存时使用）
     * @param seckillStock 库存实体
     * @return 影响行数
     */
    int insert(SeckillStock seckillStock);

    /**
     * 更新库存（对账后同步库存时使用）
     * @param seckillStock 库存实体
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(SeckillStock seckillStock);

    /**
     * 根据ID查询库存（通用方法）
     * @param id 库存ID
     * @return 库存实体
     */
    SeckillStock selectByPrimaryKey(Long id);



}