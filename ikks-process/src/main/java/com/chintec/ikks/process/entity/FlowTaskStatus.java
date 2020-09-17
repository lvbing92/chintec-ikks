package com.chintec.ikks.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
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
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 人员
     */
    private String owner;

    /**
     * assignee
     */
    private String assignee;
    /**
     * 每个节点任务方法
     */
    private String taskFunction;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
