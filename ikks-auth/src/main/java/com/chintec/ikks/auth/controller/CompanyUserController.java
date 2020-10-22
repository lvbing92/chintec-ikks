package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.service.ICompanyUserService;
import com.chintec.ikks.common.entity.vo.CompanyUserRequest;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/9 9:49
 */
@RestController
@RequestMapping(value = "v1")
@Api(value = "CompanyUser", tags = "公司管理")
public class CompanyUserController {

    @Autowired
    private ICompanyUserService iCompanyUserService;

    /**
     * 公司用户列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     * */
    @ApiOperation(value = "查询公司用户列表")
    @GetMapping("/companyUsers")
    public ResultResponse getCompanyUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                             @RequestParam(value = "currentPage", required = true) Integer currentPage,
                                             @RequestParam(value = "searchValue", required = false) String searchValue,
                                             @RequestParam(value = "sorted", required = false) String sorted,
                                             @RequestParam(value = "departmentId", required = true) Integer departmentId) {
        return iCompanyUserService.getCompanyUserList(pageSize, currentPage, searchValue, sorted,departmentId);
    }

    /**
     * 新增公司用户
     * @param companyUserRequest 公司用户信息
     * @return ResultResponse
     */
    @ApiOperation(value = "新增公司用户")
    @PostMapping("/companyUser/add")
    public ResultResponse addCompanyUser(@RequestBody CompanyUserRequest companyUserRequest) {

        return iCompanyUserService.addCompanyUser(companyUserRequest);
    }

    /**
     * 更新公司用户
     * @param companyUserRequest 公司用户信息
     * @return ResultResponse
     */
    @ApiOperation(value = "更新客户")
    @PutMapping("/companyUsers/update")
    public ResultResponse updateCompanyUser(@RequestBody CompanyUserRequest companyUserRequest) {

        return iCompanyUserService.updateCompanyUser(companyUserRequest);
    }

    /**
     * 通过Id查询公司用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "公司用户详情")
    @GetMapping("/companyUsers/{id}")
    public ResultResponse queryCompanyUser(@PathVariable Integer id) {
        return iCompanyUserService.queryCompanyUser(id);
    }
    /**
     * 删除公司用户
     *
     * @return
     */
    @ApiOperation(value = "删除公司用户")
    @DeleteMapping("/companyUsers/{id}")
    public ResultResponse deleteCompanyUser(@PathVariable Integer id) {
        return iCompanyUserService.deleteCompanyUser(id);
    }
}
