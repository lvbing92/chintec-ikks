package com.chintec.ikks.erp.request;

import com.chintec.ikks.erp.entity.Authority;
import com.chintec.ikks.erp.entity.Menu;
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
public class AuthorityRequest {

    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String authority;

    /**
     * 是否可用
     */
    private boolean enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色关联的菜单
     */
    private List<Menu> menuList;

    /**
     * 角关联的菜单Id
     */
    private String menuIds;
}
