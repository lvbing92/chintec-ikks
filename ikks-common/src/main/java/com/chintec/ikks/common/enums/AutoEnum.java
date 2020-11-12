package com.chintec.ikks.common.enums;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/11 17:36
 */
public enum AutoEnum {
    /**
     * 自动审核Enum
     */
    AUTO_ENUM_YES(2, "开启自动审核"),
    AUTO_ENUM_NO(1, "开启人工审核");
    private Integer code;
    private String message;

    AutoEnum(Integer code, String message) {
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
