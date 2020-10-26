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
public class FlowTaskStatus extends Model<FlowTaskStatus> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务实例ID
     */
    private Integer taskId;

    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 节点function
     */
    private String taskFunction;

    /**
     * 任务处理人
     */
    private Integer assignee;

    /**
     * 审批状态
     */
    private String handleStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String updataBy;

    /**
     * 节点执行条件
     */
    private String nodeExc;
    /**
     * 状态机的Id
     */
    private String statusId;
    /**
     * 驳回节点
     */
    private Integer rejectNode;
    /**
     * 是否被展示 0 是 1不是
     */
    private Integer isViewed;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
