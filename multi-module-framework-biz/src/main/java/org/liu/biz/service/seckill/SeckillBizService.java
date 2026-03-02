package org.liu.biz.service.seckill;

import org.liu.api.dto.seckill.SeckillDTO;
import org.liu.api.vo.seckill.SeckillVO;

/**
 * 秒杀业务顶层接口（定义核心方法，各实现类实现此接口）
 */
public interface SeckillBizService {
    // 执行秒杀
    void doSeckill(SeckillDTO dto);
}