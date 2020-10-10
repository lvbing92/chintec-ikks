package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.entity.Credentials;
import com.chintec.ikks.auth.mapper.AuthorityMapper;
import com.chintec.ikks.auth.request.AuthorityRequest;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * <p>
 *  角色服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService, Serializable {

    public static final String SORT_A = "A";
    public static final String SORT_D = "D";

    @Override
    public ResultResponse getRoleList(Integer pageSize, Integer currentPage, String role,
                                      String status, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_A;
        }
        //
        IPage<Authority> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Authority> authorityPage = this.page(page, new QueryWrapper<Authority>().lambda()
                .eq(Authority::getEnabled, 1)
//                .and(!StringUtils.isEmpty(searchValue), s -> s.like(Credentials::getUserName, searchValue).
//                        or().like(Credentials::getCellphone, searchValue).
//                        or().like(Credentials::getEmail, searchValue).
//                        or().like(Credentials::getUserName, searchValue))
                .orderByAsc(SORT_A.equals(sorted), Authority::getId)
                .orderByDesc(SORT_D.equals(sorted), Authority::getId));
//返回结果
        PageResultResponse<Authority> pageResultResponse = new PageResultResponse<>(authorityPage.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(authorityPage.getPages());
        pageResultResponse.setResults(authorityPage.getRecords());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse addRole(AuthorityRequest authorityRequest) {
        //查询角色是否存在
        Authority authority =getById(new QueryWrapper<Authority>()
                .lambda().eq(Authority::getAuthority,authorityRequest.getAuthority()));
        AssertsUtil.isTrue(!ObjectUtils.isEmpty(authority),"添加角色已存在！");
        BeanUtils.copyProperties(authorityRequest, authority);
        //保存角色
        this.save(authority);
        return ResultResponse.successResponse("添加角色成功！",authority);
    }

    @Override
    public ResultResponse queryRole(String id) {
        //查寻角色
        Authority authority =this.getById(new QueryWrapper<Authority>().lambda().eq(Authority::getId,id));
        //查询当前用户角色
        return ResultResponse.successResponse("查询用户详情成功",authority);
    }

    @Override
    public ResultResponse deleteRole(String id) {
        this.baseMapper.deleteById(new QueryWrapper<Authority>().lambda().eq(Authority::getId,id));
        return ResultResponse.successResponse("删除成功");
    }
}
