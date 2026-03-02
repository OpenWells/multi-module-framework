package org.liu.api.vo.user;

import lombok.Data;

/**
 * 用户登录出参VO（原有框架类，复用）
 */
@Data
public class UserLoginVO {
    private Long userId;    // 用户ID
    private String userName;// 用户名
    private String token;   // 登录令牌
}