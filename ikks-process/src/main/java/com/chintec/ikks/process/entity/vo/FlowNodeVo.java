package com.chintec.ikks.process.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:12
 */
@Data
public class FlowNodeVo {

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * 上一个节点集合
     */
    private List<Integer> proveNodes;

    /**
     * 下一个节点集合
     */
    private List<Integer> nextNodes;

    /**
     * 节点执行时间： 0，立刻 1，延迟
     */
    private String nodeRunTime;

    /**
     * 延迟时间 单位小时
     */
    private Integer delayTime;

    /**
     * 流程信息表的id
     */
    private Integer flowInformationId;

    /**
     * 节点执行条件
     */
    private String nodeExc;

    /**
     * 负责人
     */
    private Integer ownerId;

    /**
     * 节点操作按钮名称
     */
    private String nodeButtonName;

    /**
     * 节点类型：1，初始节点 2，普通节点 3，结束节点
     */
    private String nodeType;

    /**
     * 节点执行方式：1，人工 2，集成
     */
    private String nodeRunMode;

    /**
     * 功能模块id
     */
    private Integer functionModuleId;

    /**
     * 执行者角色
     */
    private String executorRole;

    /**
     * 是否允许驳回：0，否 1：是
     */
    private String isReject;

    /**
     * 驳回按钮名称
     */
    private String rejectButtonName;

    /**
     * 驳回节点Id
     */
    private Integer rejectNode;

    /**
     * 下节点走向：1，单一走向 2，多条并行 3，多选一
     */
    private String nextNodeTrend;

    /**
     * 进入下节点条件
     */
    private List<NodeFunctionVo> nodeFunctionVos;

}
