package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.AuthorityMenuMapper;
import com.chintec.ikks.auth.service.IAuthorityMenuService;
import com.chintec.ikks.common.entity.AuthorityMenu;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Service
public class AuthorityMenuServiceImpl extends ServiceImpl<AuthorityMenuMapper, AuthorityMenu>
        implements IAuthorityMenuService {

    /**
     * 获取菜单Id
     *
     * @param roleId 角色Id
     * @return List<Long>
     */
    @Override
    public List<Integer> getMenuIByRoleId(Integer roleId) {
        return this.list(new QueryWrapper<AuthorityMenu>().
                lambda().eq(AuthorityMenu::getAuthorityId, roleId))
                .stream().map(AuthorityMenu::getMenuId).collect(Collectors.toList());

    }

    /**
     * 删除
     *
     * @param roleId 角色Id
     */
    @Override
    public void deleteByRoleId(Integer roleId) {
        this.baseMapper.delete(new QueryWrapper<AuthorityMenu>().lambda().eq(AuthorityMenu::getAuthorityId, roleId));
    }

    @Override
    public boolean saveBatchAuthMenu(List<AuthorityMenu> authorityMenuList) {
        boolean flag = this.saveBatch(authorityMenuList);
        return flag;
    }

    @Override
    public void deleteByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        this.baseMapper.delete(new QueryWrapper<AuthorityMenu>().lambda().eq(AuthorityMenu::getAuthorityId, roleId)
                .and(!StringUtils.isEmpty(menuId),
                s -> s.eq(AuthorityMenu::getMenuId, menuId).
                        or().eq(AuthorityMenu::getParentId, menuId)));
    }
}
