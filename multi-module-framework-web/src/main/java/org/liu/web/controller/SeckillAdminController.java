package org.liu.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.liu.biz.service.SeckillStockWarmUpService;
import org.liu.common.config.SeckillRedisConfig;

/**
 * Nacos多模块版：预热接口（biz模块，可被api模块转发）
 */
@RestController
@RequestMapping("/seckill/admin")
@RequiredArgsConstructor
public class SeckillAdminController {

    private final SeckillStockWarmUpService warmUpService;
    private final SeckillRedisConfig seckillRedisConfig;

    /**
     * 手动预热接口
     * 访问：http://127.0.0.1:8080/seckill/admin/warmUp?activityId=1
     */
    @GetMapping("/warmUp")
    public String warmUp(@RequestParam Long activityId) {
        warmUpService.warmUpStock(activityId);
        String stockKey = seckillRedisConfig.getStockKey(activityId);
        return "预热成功，Redis Key：" + stockKey;
    }
}