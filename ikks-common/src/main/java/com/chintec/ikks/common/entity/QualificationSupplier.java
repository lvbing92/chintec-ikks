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
 * @author jeff·tang
 * @since 2020-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QualificationSupplier extends Model<QualificationSupplier> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商id
     */
    private Integer supplierId;

    /**
     * 资质id
     */
    private Integer qualificationId;

    /**
     * 该资质的内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否有效0无效1有效
     */
    private Integer isDeleted;

    /**
     * 操作人id
     */
    private Integer updateBy;

    /**
     * 操作人名字
     */
    private String updateName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
