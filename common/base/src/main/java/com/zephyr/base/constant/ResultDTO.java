package com.zephyr.base.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> implements java.io.Serializable {

    private long code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ResultDTO(long code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultDTO(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResultDTO<T> success() {
        return new ResultDTO<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(ResultCode.SUCCESS.getCode(), data, ResultCode.SUCCESS.getMessage());
    }

    public static <T> ResultDTO<T> failed(IErrorCode errorCode) {
        return new ResultDTO<>(errorCode.getCode(), null, errorCode.getMessage());
    }

    public static <T> ResultDTO<T> failed(String message) {
        return new ResultDTO<>(ResultCode.FAILED.getCode(), null, message);
    }

    public static <T> ResultDTO<T> validateFailed(String message) {
        return new ResultDTO<>(ResultCode.VALIDATION_ERROR.getCode(), null, message);
    }

    public static <T> ResultDTO<T> validateFailed(T data) {
        return new ResultDTO<>(ResultCode.VALIDATION_ERROR.getCode(), data, null);
    }


    /**
     * 未登录或token失效
     */
    public static <T> ResultDTO<T> unauthorized(T data) {
        return new ResultDTO<>(ResultCode.UNAUTHORIZED.getCode(), data, ResultCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 没有相关权限
     */
    public static <T> ResultDTO<T> forbidden(T data) {
        return new ResultDTO<>(ResultCode.FORBIDDEN.getCode(), data, ResultCode.FORBIDDEN.getMessage());
    }
}
