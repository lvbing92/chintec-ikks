package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.entity.AuthorityMenu;
import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.auth.mapper.AuthorityMapper;
import com.chintec.ikks.auth.request.AuthorityRequest;
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

import javax.management.remote.JMXAuthenticator;
import java.io.Serializable;
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
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService, Serializable {
    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private IAuthorityMenuService iAuthorityMenuService;

    private static final String SORT_A = "A";
    private static final String SORT_D = "D";

    @Override
    public ResultResponse getRoleList(Integer pageSize, Integer currentPage, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_D;
        }
        //
        IPage<Authority> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Authority> authorityPage = this.page(page, new QueryWrapper<Authority>().lambda()
                .eq(Authority::getEnabled, 1)
                .and(!StringUtils.isEmpty(searchValue),
                        s -> s.like(Authority::getAuthority, searchValue).
                                or().like(Authority::getId, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Authority::getId)
                .orderByDesc(SORT_D.equals(sorted), Authority::getCreateTime));

        List<AuthorityRequest> authorityRequestList = authorityPage.getRecords().stream().map(authority -> {
            AuthorityRequest authorityRequest = new AuthorityRequest();
            BeanUtils.copyProperties(authority, authorityRequest);
            //查询菜单信息
            List<Long> menuIByRoleId = iAuthorityMenuService.getMenuIByRoleId(authority.getId());
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
    @Transactional
    public ResultResponse addRole(AuthorityRequest authorityRequest) {
        //查询角色是否存在
        Authority authority = this.getOne(new QueryWrapper<Authority>()
                .lambda().eq(Authority::getAuthority, authorityRequest.getAuthority()));
        AssertsUtil.isTrue(!ObjectUtils.isEmpty(authority), "添加角色已存在！");
        Authority authorityMsg = new Authority();
        BeanUtils.copyProperties(authorityRequest, authorityMsg);
        authorityMsg.setCreateTime(LocalDateTime.now());
        //保存角色
        boolean saveFlag = this.save(authorityMsg);
        AssertsUtil.isTrue(!saveFlag, "新增角色失败!");
        //组装角色菜单Id
        List<AuthorityMenu> authorityMenus = authorityRequest.getMenuIds().stream().map(menuId->{
            AuthorityMenu authorityMenu = new AuthorityMenu();
            authorityMenu.setAuthorityId(authorityMsg.getId());
            authorityMenu.setMenuId(menuId);
            return authorityMenu;
        }).collect(Collectors.toList());
        //保存角色关联的菜单
        boolean flag = iAuthorityMenuService.saveBatchAuthMenu(authorityMenus);
        AssertsUtil.isTrue(!flag,"添加角色菜单失败!");
        return ResultResponse.successResponse("添加角色成功！", authority);
    }

    @Override
    @Transactional
    public ResultResponse updateRole(AuthorityRequest authorityRequest) {
        //查询角色信息
        Authority authority = this.getOne(new QueryWrapper<Authority>().lambda().eq(Authority::getId, authorityRequest.getId()));
        BeanUtils.copyProperties(authorityRequest, authority);
        authority.setUpdateTime(LocalDateTime.now());
        //更新角色信息
        boolean creFlag = this.saveOrUpdate(authority);
        AssertsUtil.isTrue(!creFlag, "编辑失败!");
        //更新当前角色菜单信息
        //删除当前角色关联的菜单
        iAuthorityMenuService.deleteByRoleId(authority.getId());
        //组装角色菜单Id
        List<AuthorityMenu> authorityMenus = authorityRequest.getMenuIds().stream().map(menuId->{
            AuthorityMenu authorityMenu = new AuthorityMenu();
            authorityMenu.setAuthorityId(authority.getId());
            authorityMenu.setMenuId(menuId);
            return authorityMenu;
        }).collect(Collectors.toList());
        //添加角色关联的菜单
        boolean flag = iAuthorityMenuService.saveBatchAuthMenu(authorityMenus);
        AssertsUtil.isTrue(!flag,"编辑角色菜单失败!");
        return ResultResponse.successResponse("编辑角色成功！");
    }
    @Override
    public ResultResponse queryRole(String id) {
        //查寻角色
        Authority authority = this.getById(new QueryWrapper<Authority>().lambda().eq(Authority::getId, id));
        //查询当前用户角色
        return ResultResponse.successResponse("查询用户详情成功", authority);
    }

    @Override
    public ResultResponse deleteRole(String id) {
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
