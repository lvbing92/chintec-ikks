package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.entity.AuthorityMenu;
import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.auth.mapper.AuthorityMapper;
import com.chintec.ikks.auth.mapper.MenuMapper;
import com.chintec.ikks.auth.request.AuthorityRequest;
import com.chintec.ikks.auth.request.MenuRequest;
import com.chintec.ikks.auth.service.IAuthorityMenuService;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
@Slf4j
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
        implements IAuthorityService {
    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private IAuthorityMenuService iAuthorityMenuService;

    //    private static final String SORT_A = "A";
    private static final String SORT_D = "D";

    /**
     * 角色列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @Override
    public ResultResponse getRoleList(Integer pageSize, Integer currentPage, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_D;
        }
        IPage<Authority> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Authority> authorityPage = this.page(page, new QueryWrapper<Authority>().lambda()
                .eq(Authority::getEnabled, 1)
                .and(!StringUtils.isEmpty(searchValue),
                        s -> s.like(Authority::getAuthority, searchValue).
                                or().like(Authority::getId, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Authority::getId)
                .orderByDesc(SORT_D.equals(sorted), Authority::getCreateTime));

        List<AuthorityRequest> authorityRequestList = authorityPage.getRecords().stream().map(authorityMsg -> {
            AuthorityRequest authorityRequest = new AuthorityRequest();
            BeanUtils.copyProperties(authorityMsg, authorityRequest);
            //查询菜单信息
            List<Long> menuIByRoleId = iAuthorityMenuService.getMenuIByRoleId(authorityMsg.getId());
            if (menuIByRoleId.size() != 0) {
                List<Menu> authorityMenus = new ArrayList<>(iMenuService.listByIds(menuIByRoleId));
                authorityRequest.setMenuList(authorityMenus);
            }
            return authorityRequest;
        }).collect(Collectors.toList());

        //返回结果
        PageResultResponse<AuthorityRequest> pageResultResponse = new PageResultResponse<>(authorityPage.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(authorityPage.getPages());
        pageResultResponse.setResults(authorityRequestList);
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public List<Authority> getAllRoleList() {
        return this.list();
    }

    /**
     * 新增角色
     *
     * @param authorityRequest 角色对象
     * @return ResultResponse
     */
    @Override
    public ResultResponse addRole(AuthorityRequest authorityRequest) {
        //查询角色是否存在
        Authority authority = this.getOne(new QueryWrapper<Authority>()
                .lambda().eq(Authority::getAuthority, authorityRequest.getAuthority()));
        AssertsUtil.isTrue(!ObjectUtils.isEmpty(authority), "添加角色已存在！");
        Authority authorityMsg = new Authority();
        BeanUtils.copyProperties(authorityRequest, authorityMsg);
        authorityMsg.setEnabled(true);
        authorityMsg.setCreateTime(LocalDateTime.now());
        //保存角色
        boolean saveFlag = this.save(authorityMsg);
        AssertsUtil.isTrue(!saveFlag, "新增角色失败!");
        return ResultResponse.successResponse("添加角色成功！", authorityMsg);
    }

    /**
     * 新增角色菜单数据
     *
     * @param menuRequest 菜单信息
     * @return ResultResponse
     */
    @Override
    public ResultResponse addRoleMenu(MenuRequest menuRequest) {
        //更新菜单信息
        Menu menu = iMenuService.getOne(new QueryWrapper<Menu>().lambda().eq(Menu::getId, menuRequest.getId()));
        menu.setUserMenuName(menuRequest.getUserMenuName());
        menu.setUserIcon(menuRequest.getUserIcon());
        iMenuService.updateById(menu);


        AuthorityMenu addAuthMenu = new AuthorityMenu();
        addAuthMenu.setMenuId(menuRequest.getId());
        addAuthMenu.setAuthorityId(menuRequest.getRoleId());
        boolean flag = iAuthorityMenuService.saveOrUpdate(addAuthMenu);
        AssertsUtil.isTrue(!flag, "添加角菜单关系失败!");

        return ResultResponse.successResponse("编辑角色菜单成功！");
    }

    /**
     * 更新角色
     *
     * @param authorityRequest 角色信息
     * @return ResultResponse
     */
    @Override
    public ResultResponse updateRole(AuthorityRequest authorityRequest) {
        //查询角色信息
        Authority authority = this.getOne(new QueryWrapper<Authority>().lambda().eq(Authority::getId, authorityRequest.getId()));
        BeanUtils.copyProperties(authorityRequest, authority);
        authority.setUpdateTime(LocalDateTime.now());
        //更新角色信息
        boolean creFlag = this.saveOrUpdate(authority);
        AssertsUtil.isTrue(!creFlag, "编辑失败!");
        return ResultResponse.successResponse("编辑角色成功！", authority);
    }

    /**
     * 更新角色菜单
     *
     * @param menuRequest 菜单信息
     * @return ResultResponse
     */
    @Override
    @Transactional
    public ResultResponse updateRoleMenu(MenuRequest menuRequest) {
        //更新菜单信息
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest, menu);
        iMenuService.addOrUpdateMenu(menu);

        //查询角色和菜单关系数据
        AuthorityMenu authorityMenu = iAuthorityMenuService.getOne(new QueryWrapper<AuthorityMenu>().lambda()
                .eq(AuthorityMenu::getAuthorityId, menuRequest.getRoleId()).eq(AuthorityMenu::getMenuId, menuRequest.getId()));
        if (ObjectUtils.isEmpty(authorityMenu)) {
            AuthorityMenu addAuthMenu = new AuthorityMenu();
            addAuthMenu.setMenuId(menuRequest.getId());
            addAuthMenu.setAuthorityId(menuRequest.getRoleId());
            boolean flag = iAuthorityMenuService.save(addAuthMenu);
            AssertsUtil.isTrue(!flag, "添加角菜单关系失败!");
        }
        return ResultResponse.successResponse("编辑角色菜单成功！");
    }

    /**
     * 通过Id查询角色详情
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse queryRole(Long id) {
        AuthorityRequest authorityRequest = new AuthorityRequest();
        //查寻角色
        Authority authority = this.getById(id);
        BeanUtils.copyProperties(authority, authorityRequest);
        //根据角色Id查询菜单
        //查询菜单信息
        List<Long> menuIds = iAuthorityMenuService.getMenuIByRoleId(authority.getId());
        List<Menu> resultMenus = new ArrayList<>();
        if (menuIds.size() != 0) {
            menuIds.forEach(menuId -> {
                List<Menu> menuList = menuMapper.getMenuList(menuId);
                resultMenus.addAll(menuList);
            });
            authorityRequest.setMenuList(resultMenus);
        }
        //查询当前用户角色
        return ResultResponse.successResponse("查询用户详情成功", authorityRequest);
    }

    @Override
    public ResultResponse deleteRole(Long id) {
        //查询用户
        Authority authority = this.getOne(new QueryWrapper<Authority>().lambda().eq(Authority::getId, id));
        authority.setEnabled(false);
        boolean flag = this.saveOrUpdate(authority);
        if (!flag) {
            return ResultResponse.failResponse("删除失败！");
        }
        return ResultResponse.successResponse("删除成功");
    }
}
