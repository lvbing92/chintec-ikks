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
 * 供应商类型
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierType extends Model<SupplierType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商类型
     */
    private String  typeName;

    /**
     * 描述
     */
    private String typeDescribe;

    /**
     * 类型状态码
     */
    private Integer typeCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    private String updateBy;

    /**
     * 创建人姓名
     */
    private String updateName;

    private Integer isDeleted;

    /**
     * 节点id
     */
    private Integer flowId;

    /**
     * 节点名称
     */
    private String flowName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
