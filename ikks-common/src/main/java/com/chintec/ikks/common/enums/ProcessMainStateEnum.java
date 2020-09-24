package com.chintec.ikks.common.enums;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/22 11:43
 */
public enum ProcessMainStateEnum {
    //主流程状态变迁
    PENDING(0, "待执行"),
    GOING(1, "进行中"),
    FINISH(2, "完成");


    private Integer code;
    private String message;

    ProcessMainStateEnum(Integer code, String message) {
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
