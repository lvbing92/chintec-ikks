package com.chintec.ikks.common.entity.vo;

import lombok.Data;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/10 11:05
 */
@Data
public class DepartmentRequest {
    /**
     * 主键id
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
     * 是否默认
     */
    private Boolean isDefault;
}
