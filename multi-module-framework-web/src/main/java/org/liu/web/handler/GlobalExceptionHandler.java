package org.liu.web.handler;

import org.liu.biz.exception.SeckillBizException;
import org.liu.common.exception.BizException;
import org.liu.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（web层，原有框架类，复用+适配秒杀）
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 统一捕获全局业务异常+秒杀专属业务异常
    @ExceptionHandler({BizException.class, SeckillBizException.class})
    public Result<Void> handleBizException(BizException e) {
        // 返回统一失败响应，code=500，msg为异常信息
        return Result.fail(e.getMessage());
    }

    // 其他异常捕获（参数校验/系统异常...）
}