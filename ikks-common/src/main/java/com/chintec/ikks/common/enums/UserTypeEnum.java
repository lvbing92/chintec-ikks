package com.chintec.ikks.common.enums;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/11/3 14:24
 */
public enum UserTypeEnum {
    ONE("1","客户管理员"),
    TWO("2","公司人员"),
    THREE("3","供应商");

    private String code;
    private String msg;

    UserTypeEnum(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
