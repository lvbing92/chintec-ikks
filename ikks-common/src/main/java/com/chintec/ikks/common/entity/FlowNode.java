package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FlowNode extends Model<FlowNode> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private String proveNodes;

    /**
     * 下一个节点集合
     */
    private String nextNodes;

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
     * 下节点走向：0，单一走向 1，多条并行 2，多选一
     */
    private String nextNodeTrend;

    /**
     * 进入下节点条件
     */
    private String nextNodeCondition;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updataBy;

    /**
     * 当前节点审核的资质id
     */
    private Integer qualificationId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
