package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.auth.entity.Credentials;
import com.chintec.ikks.auth.request.CredentialsRequest;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
public interface ICredentialsService extends IService<Credentials> {

    /**
     * 用户列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param role        角色
     * @param status      状态
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    ResultResponse getUserList(Integer pageSize, Integer currentPage, String role,
                               String status, String searchValue, String sorted);

    /**
     * 新增用户
     *
     * @param credentialsRequest 用户对象
     * @return ResultResponse
     */
    ResultResponse addUser(CredentialsRequest credentialsRequest);

    /**
     * 通过Id查询用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    ResultResponse queryUser(String id);

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    ResultResponse deleteUser(String id);
}
