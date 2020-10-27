package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/16 10:59
 */
@Data
public class CompanyUserRequest {
    @ApiModelProperty(value = "公司用户Id", hidden = true)
    private Integer id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", hidden = true)
    private String companyName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", hidden = true)
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 所属部门Id
     */
    @ApiModelProperty(value = "所属部门Id", hidden = true)
    private Integer departmentId;

    /**
     * 角色Id
     */
    @ApiModelProperty(value = "角色Id")
    private Integer roleId;

}
