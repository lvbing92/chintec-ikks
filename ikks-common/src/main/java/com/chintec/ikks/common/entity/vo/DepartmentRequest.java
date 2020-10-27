package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "主键id", hidden = true)
    private Integer id;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用", hidden = true)
    private Boolean enabled;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认", hidden = true)
    private Boolean isDefault;
}
