package com.chintec.ikks.erp.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "TDepartment", description = "部门信息")
public class Department {

    private static final long serialVersionUID = 1L;

    /**
     * 部门Id
     */
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


}
