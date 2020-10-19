package com.chintec.ikks.erp.request;

import lombok.Data;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/16 10:59
 */
@Data
public class CompanyUserRequest {
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属部门Id
     */
    private Integer departmentId;

    /**
     * 角色Id
     */
    private Integer roleId;

}
