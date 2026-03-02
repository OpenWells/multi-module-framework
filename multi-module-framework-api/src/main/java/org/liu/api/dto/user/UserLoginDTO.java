package org.liu.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录/注册入参DTO（原有框架类，复用）
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "手机号不能为空")
    private String phone;    // 手机号

    @NotBlank(message = "密码不能为空")
    private String password; // 密码

    private String userName; // 用户名（仅注册用）
}