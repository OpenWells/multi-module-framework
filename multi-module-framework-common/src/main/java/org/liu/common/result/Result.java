package org.liu.common.result;

import lombok.Data;

/**
 * 通用返回结果（Spring Boot3+JDK17适配）
 */
@Data
public class Result<T> {
    /**
     * 响应码：200成功，500失败，400参数错误
     */
    private int code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    // 静态工厂方法（JDK17支持var，此处简化）
    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        return error(500, msg);
    }
}