package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.request.CompanyUserRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 公司用户表 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
@FeignClient(value = "ikks-auth", path = "v1")
public interface ICompanyUserService {

    /**
     * 公司用户列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "查询公司用户列表")
    @GetMapping("/companyUsers")
    ResultResponse getCompanyUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage", required = true) Integer currentPage,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted,
                                      @RequestParam(value = "departmentId", required = true) Integer departmentId);

    /**
     * 新增公司用户
     *
     * @param companyUserRequest 公司用户信息
     * @return ResultResponse
     */
    @ApiOperation(value = "新增公司用户")
    @PostMapping("/companyUser/add")
    ResultResponse addCompanyUser(@RequestBody CompanyUserRequest companyUserRequest);

    /**
     * 更新公司用户
     *
     * @param companyUserRequest 公司用户信息
     * @return ResultResponse
     */
    @ApiOperation(value = "更新客户")
    @PutMapping("/companyUsers/update")
    ResultResponse updateCompanyUser(@RequestBody CompanyUserRequest companyUserRequest);

    /**
     * 通过Id查询公司用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "公司用户详情")
    @GetMapping("/companyUsers/{id}")
    ResultResponse queryCompanyUser(@PathVariable Integer id);

    /**
     * 删除公司用户
     *
     * @return
     */
    @ApiOperation(value = "删除公司用户")
    @DeleteMapping("/companyUsers/{id}")
    ResultResponse deleteCompanyUser(@PathVariable Integer id);
}
