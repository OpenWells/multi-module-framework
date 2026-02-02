package org.liu.common.exception;

import lombok.Getter;

/**
 * 业务异常（Spring Boot3适配：继承RuntimeException）
 */
@Getter
public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        this(500, message);
    }
}