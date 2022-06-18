package com.zephyr.base.constant;

public enum ReturnCode {
    SUCCESS("2000"),

    /**
     * 验证错误 3XXX
     */
    VALIDATION_ERROR("3000"),

    /**
     * 运行时异常 4XXX
     */
    RUNTIME_EXCEPTION("4000"),
    /**
     * 请求方式错误异常
     */
    HttpRequestMethodNotSupportedException("4001", "REQUEST_METHOD_UNSUPPORTED_EX"),
    /**
     * 空指针异常
     */
    NullPointerException("4002", "NULL_POINTER_EX"),
    /**
     * 类型强制转换异常
     */
    ClassCastException("4003", "CLASS_CAST_EX"),
    /**
     * 数字格式异常
     */
    NumberFormatException("4004", "NUMBER_FORMAT_EX"),
    /**
     * 数组下标越界异常
     */
    ArrayIndexOutOfBoundsException("4005", "INDEX_OUT_OF_BNOUND_EX"),
    /**
     * 算术异常
     */
    ArithmeticException("4006", "ARITHMETIC_EX"),

    //---------------------security-oauth2 异常---------------------------
    /**
     * 授权失败，禁止访问
     */
    AccessDeniedException("403", "FORBIDDEN_EX"),

    /**
     * 授权失败，禁止访问
     */
    AuthenticationException("401", "UNAUTHORIZED_EX");

    private String code;
    private String msg;

    ReturnCode(String code) {
        this.code = code;
    }

    ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean contains(String name) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (returnCode.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
