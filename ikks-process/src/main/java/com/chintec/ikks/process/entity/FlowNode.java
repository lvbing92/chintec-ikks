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
     * 是否延迟任务 0否 1是
     */
    private Boolean timeTask;

    /**
     * 延迟时间 单位小时
     */
    private Integer time;

    /**
     * 负责人
     */
    private Integer ownId;

    /**
     * 流程信息表的id
     */
    private Integer flowInformationId;

    /**
     * 节点执行条件
     */
    private String nodeExc;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
