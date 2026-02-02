package org.liu.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户VO（视图展示对象）
 */
@Data
public class UserVO implements Serializable {

    // 序列化版本号（可选，建议添加，保证序列化兼容性）
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号（脱敏）
     */
    private String phone;
    /**
     * 创建时间
     */
    private String createTime;
}