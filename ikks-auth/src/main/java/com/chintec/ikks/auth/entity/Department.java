package com.chintec.ikks.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Department", description = "部门信息")
public class Department extends Model<Department> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 是否默认
     */
    private Boolean isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人Id
     */
    private Integer updateById;

    /**
     * 更新人名称
     */
    private String updateByName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
