package com.chintec.ikks.common.enums;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/9 11:15
 */
public enum ModelResultEnum {
    /**
     * 各个节点任务返回状态
     */
    RESULT_REFUSE(0, "拒绝"),
    RESULT_PASS(1, "通过");
    private Integer code;
    private String message;

    ModelResultEnum(Integer code, String message) {
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
