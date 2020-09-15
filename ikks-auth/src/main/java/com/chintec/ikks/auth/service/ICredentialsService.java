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
     * @param pageSize
     * @param currentPage
     * @param role
     * @param status
     * @param searchValue
     * @param sorted
     * @return
     */
    ResultResponse getUserList(Integer pageSize, Integer currentPage, String role,
                               String status, String searchValue, String sorted);

    /**
     * 新增用户
     *
     * @return
     */
    ResultResponse addUser(CredentialsRequest credentialsRequest);

    /**
     * 通过Id查询用户
     *
     * @param id
     * @return
     */
    public ResultResponse queryUser(String id);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public ResultResponse deleteUser(String id);
}
