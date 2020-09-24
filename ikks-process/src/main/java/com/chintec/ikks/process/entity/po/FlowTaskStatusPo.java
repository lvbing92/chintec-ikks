package com.chintec.ikks.process.entity.po;

import com.chintec.ikks.common.enums.NodeStateEnum;
import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 9:50
 */
@Data
public class FlowTaskStatusPo {
    /**
     * 状态任务id
     */
    private String id;
    /**
     * 任务名字
     */
    private String name;
    /**
     * 任务所处的状态
     */
    private NodeStateEnum status;
    /**
     * 延时时间
     */
    private String time;
    /**
     * 任务内容
     */
    private Object data;
}
