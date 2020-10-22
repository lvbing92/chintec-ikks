package com.chintec.ikks.common.entity.vo;

import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.Menu;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/8 9:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityRequest extends Authority {

    /**
     * 角色关联的菜单
     */
    private List<Menu> menuList;
    /**
     * 角色关联的菜单Ids
     */
    private String menuIds;
    /**
     * 角色关联的菜单
     */
    private List<AuthorityMenuResponse> authorityMenuResponseList;
}
