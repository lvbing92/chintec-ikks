package com.chintec.ikks.auth.response;

import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.entity.CompanyUser;
import com.chintec.ikks.auth.entity.Department;
import lombok.Data;

import java.util.List;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/16 10:09
 */
@Data
public class DepartmentResponse {
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

    /**
     * 部门人员数量
     */
    private int countCompanyUser;
    /**
     * 角色集合
     */
    private List<Authority> authorities;
    /**
     * 部门人员数量
     */
//    List<CompanyUser> companyUserList;
}
