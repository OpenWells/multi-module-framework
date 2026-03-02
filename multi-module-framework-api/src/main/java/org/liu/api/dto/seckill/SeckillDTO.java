package org.liu.api.dto.seckill;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 秒杀入参DTO（web层接收请求参数，做参数校验）
 */
@Data
public class SeckillDTO {
    @NotNull(message = "秒杀活动ID不能为空")
    private Long activityId; // 秒杀活动ID

    @NotNull(message = "用户ID不能为空")
    private Long userId;     // 用户ID（生产从Token解析，此处简化）
}