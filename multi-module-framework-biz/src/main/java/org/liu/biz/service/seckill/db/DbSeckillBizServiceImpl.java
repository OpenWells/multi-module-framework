package org.liu.biz.service.seckill.db;

import jakarta.annotation.Resource;
import org.liu.api.dto.seckill.SeckillDTO;
import org.liu.api.enums.SeckillStatusEnum;
import org.liu.api.vo.seckill.SeckillVO;
import org.liu.biz.service.user.SysUserBizService;
import org.liu.biz.util.SeckillBizUtil;
import org.liu.core.entity.seckill.SeckillActivity;
import org.liu.core.entity.seckill.SeckillOrder;
import org.liu.core.service.seckill.SeckillActivityDataService;
import org.liu.core.service.seckill.SeckillOrderDataService;
import org.liu.core.service.seckill.SeckillStockDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀业务实现-数据库乐观锁方案（基础版，适合单机/低并发）
 */
@Service("dbSeckillBizService")
public class DbSeckillBizServiceImpl  {

    @Resource
    private SeckillActivityDataService activityDataService;
    @Resource
    private SeckillStockDataService stockDataService;
    @Resource
    private SeckillOrderDataService orderDataService;
    @Resource
    private SysUserBizService userBizService;
    @Resource
    private SeckillBizUtil seckillBizUtil;

    @Transactional(rollbackFor = Exception.class)
    public SeckillVO doSeckill(SeckillDTO dto) {
        SeckillVO vo = new SeckillVO();
        Long activityId = dto.getActivityId();
        Long userId = dto.getUserId();

        // 1. 校验用户有效性
        if (!userBizService.validateUser(userId)) {
            vo.setSuccess(false);
            vo.setMsg("用户无效");
            return vo;
        }

        // 2. 校验秒杀活动
        SeckillActivity activity = activityDataService.getById(activityId);
        if (activity == null || !SeckillStatusEnum.ACTIVITY_ON_GOING.getCode().equals(activity.getStatus())) {
            vo.setSuccess(false);
            vo.setMsg("秒杀活动异常");
            return vo;
        }

        // 3. 防重校验
        if (orderDataService.getByUserIdAndActivityId(userId, activityId) != null) {
            vo.setSuccess(false);
            vo.setMsg("您已下单，请勿重复秒杀");
            return vo;
        }

        // 4. 乐观锁扣减库存（返回受影响行数，0表示扣减失败）
        int decrResult = stockDataService.decrStockByActivityId(activityId);
        if (decrResult == 0) {
            vo.setSuccess(false);
            vo.setMsg("库存不足，秒杀失败");
            return vo;
        }

        // 5. 创建秒杀订单
        SeckillOrder order = new SeckillOrder();
        order.setActivityId(activityId);
        order.setGoodsId(activity.getGoodsId());
        order.setUserId(userId);
        order.setOrderNo(seckillBizUtil.generateOrderNo());
        order.setStatus(SeckillStatusEnum.ORDER_WAIT_PAY.getCode());
        orderDataService.save(order);

        // 6. 返回秒杀成功结果
        vo.setSuccess(true);
        vo.setOrderNo(order.getOrderNo());
        vo.setMsg("秒杀成功");
        return vo;
    }
}