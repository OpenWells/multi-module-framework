package org.liu.web.controller.seckill;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.liu.api.dto.seckill.SeckillDTO;
import org.liu.api.vo.seckill.SeckillVO;
import org.liu.biz.service.seckill.SeckillBizService;
import org.liu.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀接口控制器（web层仅做请求接入，无任何业务逻辑）
 * 生产推荐使用Redis+Lua方案：@Resource(name = "redisLuaSeckillBizService")
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    // 注入Redis+Lua秒杀实现（生产最优），切换方案仅需修改name值为dbSeckillBizService
    @Resource(name = "redisLuaSeckillBizService")
    private SeckillBizService seckillBizService;



    /**
     * 执行秒杀接口
     */
    @PostMapping("/doSeckill")
    public Result<SeckillVO> doSeckill(@Valid @RequestBody SeckillDTO dto) {
        // 仅调用biz层方法，无任何业务逻辑
        SeckillVO vo = new SeckillVO();
        seckillBizService.doSeckill(dto);
        return Result.success(vo);
    }


}