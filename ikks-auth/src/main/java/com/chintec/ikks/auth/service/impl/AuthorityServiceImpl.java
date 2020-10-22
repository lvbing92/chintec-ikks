package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.AuthorityMapper;
import com.chintec.ikks.auth.service.IAuthorityMenuService;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.AuthorityMenu;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import com.chintec.ikks.common.entity.response.AuthorityResponse;
import com.chintec.ikks.common.entity.vo.AuthorityRequest;
import com.chintec.ikks.common.entity.vo.MenuRequest;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.MenuTree;
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

        //查询当前角色关联的菜单树结构
        List<AuthorityResponse> authorityResponseList = authorityPage.getRecords().stream().map(authorityMsg -> {
            //角色结果
            AuthorityResponse authorityResponse = new AuthorityResponse();
            BeanUtils.copyProperties(authorityMsg, authorityResponse);
            //查询菜单信息
            List<AuthorityMenu> authorityMenuList = iAuthorityMenuService.list(new QueryWrapper<AuthorityMenu>()
                    .lambda().eq(AuthorityMenu::getAuthorityId, authorityMsg.getId()));
            //如果菜单不为空，将菜单以树状结构处理
            if (authorityMenuList.size() != 0) {
                List<AuthorityMenuResponse> collect = authorityMenuList.stream().map(authorityMenu -> {
                    AuthorityMenuResponse authorityMenuResponse = new AuthorityMenuResponse();
                    BeanUtils.copyProperties(authorityMenu, authorityMenuResponse);
                    return authorityMenuResponse;
                }).collect(Collectors.toList());
                authorityResponse.setAuthorityMenuResponseList(MenuTree.getMenuTrees(collect));
            }
            return authorityResponse;
        }).collect(Collectors.toList());

        //返回结果
        PageResultResponse<AuthorityResponse> pageResultResponse = new PageResultResponse<>(authorityPage.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(authorityPage.getPages());
        pageResultResponse.setResults(authorityResponseList);
        return ResultResponse.successResponse(pageResultResponse);
    }

    /**
     * 查询所有角色
     *
     * @return List
     */
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
        //校验数据是否存在
        AuthorityMenu isExist = iAuthorityMenuService.getOne(new QueryWrapper<AuthorityMenu>().lambda()
                .eq(AuthorityMenu::getAuthorityId, menuRequest.getRoleId()).eq(AuthorityMenu::getMenuId, menuRequest.getId()));
        if (!ObjectUtils.isEmpty(isExist)) {
            return ResultResponse.successResponse("当前菜单已存在!");
        }
        AuthorityMenu addAuthMenu = new AuthorityMenu();
        addAuthMenu.setMenuId(menuRequest.getId());
        addAuthMenu.setAuthorityId(menuRequest.getRoleId());
        addAuthMenu.setMenuName(menuRequest.getMenuName());
        addAuthMenu.setMenuIcon(menuRequest.getIcon());
        addAuthMenu.setParentId(menuRequest.getParentId());
        boolean flag = iAuthorityMenuService.save(addAuthMenu);
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

        //查询角色和菜单关系数据
        AuthorityMenu authorityMenu = iAuthorityMenuService.getOne(new QueryWrapper<AuthorityMenu>().lambda()
                .eq(AuthorityMenu::getAuthorityId, menuRequest.getRoleId()).eq(AuthorityMenu::getMenuId, menuRequest.getId()));
        if (!ObjectUtils.isEmpty(authorityMenu)) {
            authorityMenu.setMenuName(menuRequest.getMenuName());
            authorityMenu.setMenuIcon(menuRequest.getIcon());
            boolean flag = iAuthorityMenuService.update(authorityMenu, new QueryWrapper<AuthorityMenu>().lambda()
                    .eq(AuthorityMenu::getMenuId, menuRequest.getId()).eq(AuthorityMenu::getAuthorityId, menuRequest.getRoleId()));
            AssertsUtil.isTrue(!flag, "添加角菜单关系失败!");
        } else {
            return ResultResponse.failResponse("查无当前菜单!");
        }
        return ResultResponse.successResponse("编辑角色菜单成功!");
    }

    /**
     * 通过Id查询角色详情
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse queryRole(Long id) {
        AuthorityResponse authorityResponse = new AuthorityResponse();
        //查寻角色
        Authority authority = this.getById(id);
        BeanUtils.copyProperties(authority, authorityResponse);
        //根据角色Id查询菜单
        //查询菜单信息
        List<AuthorityMenu> authorityMenuList = iAuthorityMenuService.list(new QueryWrapper<AuthorityMenu>()
                .lambda().eq(AuthorityMenu::getAuthorityId, id));
        List<AuthorityMenuResponse> resultMenus = new ArrayList<>();
        if (authorityMenuList.size() != 0) {
            List<AuthorityMenuResponse> collect = authorityMenuList.stream().map(authorityMenu -> {
                AuthorityMenuResponse authorityMenuResponse = new AuthorityMenuResponse();
                BeanUtils.copyProperties(authorityMenu, authorityMenuResponse);
                return authorityMenuResponse;
            }).collect(Collectors.toList());
            resultMenus = MenuTree.getMenuTrees(collect);
        }
        authorityResponse.setAuthorityMenuResponseList(resultMenus);
        //查询当前用户角色
        return ResultResponse.successResponse("查询用户详情成功", authorityResponse);
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
