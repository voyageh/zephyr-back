package com.zephyr.base.constant;

public enum ResultCode implements IErrorCode {
    SUCCESS(200),
    VALIDATION_ERROR(300),
    FAILED(500, "操作失败"),
    RUNTIME_EXCEPTION(400),
    NOTFOUND(404, "参数检验失败"),
    UNAUTHORIZED(401, "未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResultCode(long code) {
        this.code = code;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
