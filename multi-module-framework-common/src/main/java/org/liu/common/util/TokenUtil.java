package org.liu.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 通用Token工具（简化版，生产可替换为JWT，原有框架类，复用）
 */
@Component
public class TokenUtil {
    // 生成用户登录Token
    public String generateToken(String userId) {
        return userId + "_" + UUID.randomUUID().toString().replace("-", "");
    }
}