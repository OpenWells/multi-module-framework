package org.liu.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局统一响应结果（所有接口返回此格式，原有框架类，复用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    // 响应码：200成功，500业务异常，400参数异常，401未授权，404资源不存在
    private Integer code;
    // 响应信息
    private String msg;
    // 响应数据
    private T data;

    // 成功返回（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 成功返回（无数据）
    public static Result<Void> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 业务失败返回
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    // 参数错误返回
    public static <T> Result<T> paramError(String msg) {
        return new Result<>(400, msg, null);
    }
}