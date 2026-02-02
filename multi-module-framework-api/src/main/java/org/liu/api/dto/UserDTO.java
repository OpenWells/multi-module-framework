package org.liu.api.dto;

import lombok.Data;

/**
 * 用户DTO（数据传输对象）
 * Spring Boot3适配：jakarta.validation.constraints（代替javax）
 */
@Data
public class UserDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
}