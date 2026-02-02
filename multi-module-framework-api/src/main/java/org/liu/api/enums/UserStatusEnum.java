package org.liu.api.enums;

import lombok.Getter;

/**
 * 用户状态枚举（JDK17支持增强枚举）
 */
@Getter
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    DISABLE(0, "禁用");

    private final int code;
    private final String desc;

    UserStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // JDK17增强：静态方法简化
    public static UserStatusEnum getByCode(int code) {
        for (UserStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的用户状态码：" + code);
    }
}