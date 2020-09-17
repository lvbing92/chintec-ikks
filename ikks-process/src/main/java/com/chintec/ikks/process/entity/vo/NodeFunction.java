package com.chintec.ikks.process.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 9:22
 */
@Data
public class NodeFunction {
    /**
     * 节点分支方法类型
     */
    private String type;
    /**
     * 状态
     */
    private String status;
    /**
     * 每个状态要执行的节点
     */
    private List<Integer> flowNodeId;
    /**
     * 多分支code值
     */
    private String code;
    /**
     * 该方法属于哪儿个流程
     */
    private Integer flowId;
}
