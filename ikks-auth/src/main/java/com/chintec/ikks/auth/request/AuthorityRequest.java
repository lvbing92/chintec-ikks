package com.chintec.ikks.auth.request;

import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.entity.Menu;
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
     * 角关联的菜单Id
     */
//    private List<Long> menuIds;
    /**
     * 角色关联的菜单Ids
     */
    private String menuIds;
}
