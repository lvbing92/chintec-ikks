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
public class FlowNodeFunction extends Model<FlowNodeFunction> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程节点id
     */
    private Integer flowId;

    /**
     * 流程类型：0顺序 1 boolean 2 enum
     */
    private Boolean functionType;

    /**
     * 流程方法对应的类型和节点type:2  status: true  flow_node_id: 13
type:2  status: false  flow_node_id: 14
     */
    private String functionContent;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
