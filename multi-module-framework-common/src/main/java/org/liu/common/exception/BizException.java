package org.liu.common.exception;

/**
 * 全局业务异常（所有业务模块统一抛出，原有框架类，复用）
 * 作用：作为业务层异常根类，替代原生RuntimeException，方便全局统一捕获处理
 * 所有业务专属异常（如秒杀、用户）均继承此类
 */
public class BizException extends RuntimeException {
    // 仅携带异常信息
    public BizException(String message) {
        super(message);
    }

    // 携带异常信息+底层异常原因（便于排查根因，如数据库/Redis异常）
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}