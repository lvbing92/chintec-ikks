package com.chintec.ikks.common.enums;

/**
 * 此类提供auth所有的responseCode
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/24 14:02
 */
public enum CommonCodeEnum {
    //公共的失败code
    COMMON_FALSE_CODE(40000, "FALSE"),
    //公共的参数错误code
    PARAMS_ERROR_CODE(40001, "参数错误"),
    //成功的code
    COMMON_SUCCESS_CODE(200, "SUCCESS");

    private Integer code;
    private String message;

    CommonCodeEnum(Integer code, String message) {
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
