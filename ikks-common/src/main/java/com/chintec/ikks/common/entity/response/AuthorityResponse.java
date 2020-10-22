package com.chintec.ikks.common.entity.response;

import lombok.Data;

import java.util.List;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/19 18:33
 */
@Data
public class AuthorityResponse {

    private Long id;
    /**
     * 角色名称
     */
    private String authority;
    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 角色关联的菜单
     */
    private List<AuthorityMenuResponse> authorityMenuResponseList;
}
