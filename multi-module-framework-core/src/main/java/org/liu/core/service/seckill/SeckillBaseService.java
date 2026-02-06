package org.liu.core.service.seckill;



import com.baomidou.mybatisplus.extension.service.IService;
import org.liu.core.entity.seckill.SeckillActivity;
import org.liu.core.entity.seckill.SeckillOrder;
import org.liu.core.entity.seckill.SeckillStock;

/**
 * 秒杀基础Service，所有方案复用
 */
public interface SeckillBaseService extends IService<SeckillOrder> {
    // 验证活动状态
    boolean validateActivity(SeckillActivity activity);
    // 验证用户是否已下单
    boolean isUserOrdered(Long activityId, Long userId);
    // 获取秒杀活动信息
    SeckillActivity getSeckillActivity(Long activityId);
    // 获取秒杀库存信息
    SeckillStock getSeckillStock(Long activityId);
    // 创建秒杀订单
    boolean createSeckillOrder(Long activityId, Long goodsId, Long userId);
}