package com.chintec.ikks.common.enums;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/22 11:46
 */
public enum NodeStateEnum {
    //节点任务状态
    PENDING(0, "待执行"),
    GOING(1, "进行中"),
    CHOICE_ONE(5, "选择"),
    PASS(2, "通过"),
    CHOICE_TWO(6, "选择"),
    REFUSE(3, "拒绝"),
    REFUSE_FINISH(3, "结束");

    private Integer code;
    private String message;

    NodeStateEnum(Integer code, String message) {
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
