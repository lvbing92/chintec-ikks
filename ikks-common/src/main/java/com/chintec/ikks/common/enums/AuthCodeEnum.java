package com.chintec.ikks.common.enums;

/**
 * 此类提供auth所有的responseCode
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/24 14:02
 */
public enum AuthCodeEnum {
    //用户未登录code
    NO_LOGIN_CODE(14001, "用户未登录"),
    //用户登录失败code
    FAIL_LOGIN_CODE(14002, "用户登录失败"),
    //没有权限code
    NO_AUTH_CODE(14003, "该用户没有权限");

    private Integer code;
    private String message;

    AuthCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
