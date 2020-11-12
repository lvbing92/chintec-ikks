package com.chintec.ikks.common.enums;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/12 9:50
 */
public enum NodeTypeEnum {
    /**
     * 节点类型
     */
    NODE_TYPE_ENUM_START(1, "起始节点"),
    NODE_TYPE_ENUM_NORMAL(2, "普通节点"),
    NODE_TYPE_ENUM_FINISH(3, "完成节点"),
    NODE_TYPE_ENUM_EXC_SINGLE(1, "顺序"),
    NODE_TYPE_ENUM_EXC_CHOICE(2, "多选一"),
    NODE_TYPE_ENUM_EXC_MORE(3, "多条并行");

    private Integer code;

    private String message;

    NodeTypeEnum(Integer code, String message) {
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
