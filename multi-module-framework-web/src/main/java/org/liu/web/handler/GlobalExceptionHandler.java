package org.liu.web.handler;

import org.liu.biz.exception.SeckillBizException;
import org.liu.common.exception.BizException;
import org.liu.common.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（web层统一处理所有异常，返回统一Result格式）
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理秒杀业务异常+全局业务异常
    @ExceptionHandler({BizException.class, SeckillBizException.class})
    public Result<Void> handleBizException(BizException e) {
        return Result.fail(e.getMessage());
    }

    // 处理参数校验异常（@Valid注解）
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.paramError(msg);
    }

    // 处理系统未知异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleSystemException(Exception e) {
        e.printStackTrace(); // 生产需替换为日志框架
        return Result.fail("系统异常，请联系管理员");
    }
}