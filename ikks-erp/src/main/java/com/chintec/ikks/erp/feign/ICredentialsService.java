package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.CredentialsRequest;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
@FeignClient(value = "ikks-auth",path = "/v1")
public interface ICredentialsService {

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
    @ApiOperation(value = "用户列表查询")
    @GetMapping(value = "/users")
    ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "currentPage", required = true) Integer currentPage,
                               @RequestParam(value = "role", required = false) String role,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "searchValue", required = false) String searchValue,
                               @RequestParam(value = "sorted", required = false) String sorted);

    /**
     * 新增用户
     *
     * @param credentialsRequest 用户对象
     * @return ResultResponse
     */
    @ApiOperation(value = "用户新增")
    @GetMapping(value = "/user/add")
    ResultResponse addUser(@RequestBody CredentialsRequest credentialsRequest);

    /**
     * 通过Id查询用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    @ApiOperation(value = "用户列表查询")
    @GetMapping(value = "/user/{id}")
    ResultResponse queryUser(@PathVariable Long id);

    /**
     * 更新客户
     * @param credentialsRequest 客户信息
     * @return ResultResponse
     */
    @ApiOperation(value = "更新客户")
    @PutMapping("/user/update")
    ResultResponse updateUser(CredentialsRequest credentialsRequest);

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/user/{id}")
    ResultResponse deleteUser(@PathVariable Long id);
}
