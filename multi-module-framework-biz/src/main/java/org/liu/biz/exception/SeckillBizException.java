package org.liu.biz.exception;

import org.liu.common.exception.BizException;

/**
 * 秒杀专属业务异常（继承全局业务异常，仅秒杀模块使用，原有框架无，新增）
 */
public class SeckillBizException extends BizException {
    public SeckillBizException(String message) {
        super(message);
    }

    public SeckillBizException(String message, Throwable cause) {
        super(message, cause);
    }
}