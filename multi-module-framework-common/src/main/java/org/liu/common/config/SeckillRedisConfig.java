package org.liu.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 秒杀Redis配置读取类（绑定seckill.redis配置）
 */
@Component
@ConfigurationProperties(prefix = "seckill.redis") // 绑定seckill.redis前缀
@Data // lombok自动生成getter/setter，需引入lombok依赖
public class SeckillRedisConfig {
    // 对应seckill.redis.key-prefix
    private KeyPrefix keyPrefix;
    // 对应seckill.redis.order-no-prefix
    private String orderNoPrefix;

    /**
     * 内部类：绑定key-prefix子节点
     */
    @Data
    public static class KeyPrefix {
        // 对应seckill.redis.key-prefix.stock
        private String stock;
        // 对应seckill.redis.key-prefix.user-order（YAML的kebab-case自动转驼峰）
        private String userOrder;
    }

    /**
     * 生成库存Key（前缀+活动ID）
     * @param activityId 秒杀活动ID
     * @return 完整库存Key，如：seckill_stock_1
     */
    public String getStockKey(Long activityId) {
        return keyPrefix.getStock() + activityId;
    }

    /**
     * 生成用户防重Key（前缀+活动ID）
     * @param activityId 秒杀活动ID
     * @return 完整用户防重Key，如：seckill_user_order_1
     */
    public String getUserOrderKey(Long activityId) {
        return keyPrefix.getUserOrder() + activityId;
    }
}