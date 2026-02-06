package org.liu.common.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通用密码加密工具（MD5+盐，生产可替换为BCrypt，原有框架类，复用）
 */
@Component
public class PasswordUtil {
    // 盐值（生产可移到Nacos配置中心）
    private static final String SALT = "multi_module_2026";

    // 密码加密
    public String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest((password + SALT).getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    // 密码匹配
    public boolean matches(String rawPassword, String encryptPassword) {
        return encrypt(rawPassword).equals(encryptPassword);
    }
}