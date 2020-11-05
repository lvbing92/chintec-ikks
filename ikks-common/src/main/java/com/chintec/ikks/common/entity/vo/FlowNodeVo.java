package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:12
 */
@Data
@ApiModel
public class FlowNodeVo {

    private Integer id;
    /**
     * 节点名称
     */
    @NotBlank(message = "节点名称不能为空")
    @ApiModelProperty("节点名称")
    private String nodeName;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id,一般是1,2,3,4,5,6")
    private Integer nodeId;

    /**
     * 上一个节点集合
     */
    @ApiModelProperty("上一个节点id 集合")
    private List<Integer> proveNodes;

    /**
     * 下一个节点集合
     */
    @ApiModelProperty("下一个节点id集合")
    private List<Integer> nextNodes;

    /**
     * 节点执行时间： 0，立刻 1，延迟
     */
    @ApiModelProperty("节点执行时刻 是否延时")
    private String nodeRunTime;

    /**
     * 延迟时间 单位小时
     */
    @ApiModelProperty("延时时间")
    private Integer delayTime;

    /**
     * 流程信息表的id
     */
    @ApiModelProperty("流程信息id")
    private Integer flowInformationId;

    /**
     * 节点执行条件
     */
    @ApiModelProperty("节点执行条件是1 顺序  2多条并行 3多选一")
    private String nodeExc;

    /**
     * 负责人
     */
    @ApiModelProperty("负责人")
    @NotBlank(message = "负责人不能为空")
    private Integer ownerId;

    /**
     * 节点操作按钮名称
     */
    @ApiModelProperty("操作按钮名称")
    private String nodeButtonName;

    /**
     * 节点类型：1，初始节点 2，普通节点 3，结束节点
     */
    @ApiModelProperty("节点类型：1，初始节点 2，普通节点 3，结束节点")
    private String nodeType;

    /**
     * 节点执行方式：1，人工 2，集成
     */
    @ApiModelProperty("节点执行方式：1，人工 2，集成")
    private String nodeRunMode;

    /**
     * 功能模块id
     */
    @ApiModelProperty("功能模块id")
    private Integer functionModuleId;

    /**
     * 执行者角色
     */
    @ApiModelProperty(value = "执行者角色", hidden = true)
    private String executorRole;

    /**
     * 是否允许驳回：0，否 1：是
     */
    @ApiModelProperty("是否允许驳回：0，否 1：是")
    private String isReject;

    /**
     * 驳回按钮名称
     */
    @ApiModelProperty("驳回按钮名称")
    private String rejectButtonName;

    /**
     * 驳回节点Id
     */
    @ApiModelProperty("驳回节点id")
    private Integer rejectNode;

    /**
     * 下节点走向：1，单一走向 2，多条并行 3，多选一
     */
    @NotBlank(message = "下节点走向不能为空")
    @ApiModelProperty("下节点走向：1，单一走向 2，多条并行 3，多选一")
    private String nextNodeTrend;

    /**
     * 进入下节点条件
     */
    @ApiModelProperty("进入下节点条件")
    private List<NodeFunctionVo> nodeFunctionVos;

    @ApiModelProperty("资质文档id")
    private Integer qualificationId;

}
