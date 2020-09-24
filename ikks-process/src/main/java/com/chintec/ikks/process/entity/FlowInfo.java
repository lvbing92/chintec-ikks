package com.chintec.ikks.process.entity;

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
public class FlowInfo extends Model<FlowInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程名字
     */
    private String flowName;

    /**
     * 停用：0，启用：1
     */
    private String flowStatus;

    /**
     * 模块ID
     */
    private Integer moduleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String updataBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
