package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.auth.entity.CompanyUser;
import com.chintec.ikks.auth.request.CompanyUserRequest;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 公司用户表 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
public interface ICompanyUserService extends IService<CompanyUser> {

    /**
     * 公司用户列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    ResultResponse getCompanyUserList(Integer pageSize, Integer currentPage, String searchValue, String sorted,Integer departmentId);

    /**
     * 新增公司用户
     *
     * @param companyUserRequest 公司用户对象
     * @return ResultResponse
     */
    ResultResponse addCompanyUser(CompanyUserRequest companyUserRequest);

    /**
     * 更新公司用户
     *
     * @param companyUserRequest 更新公司用户
     * @return ResultResponse
     */
    ResultResponse updateCompanyUser(CompanyUserRequest companyUserRequest);

    /**
     * 通过Id查询公司用户
     *
     * @param id 公司用户Id
     * @return ResultResponse
     */
    ResultResponse queryCompanyUser(Integer id);

    /**
     * 删除公司用户
     *
     * @param id 公司用户Id
     * @return ResultResponse
     */
    ResultResponse deleteCompanyUser(Integer id);
}
