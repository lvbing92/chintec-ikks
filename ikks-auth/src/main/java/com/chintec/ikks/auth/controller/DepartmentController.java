package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.request.DepartmentRequest;
import com.chintec.ikks.auth.service.IDepartmentService;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/2 9:50
 */
@RestController
@RequestMapping(value = "v1")
@Api(value = "auth", tags = "部门管理")
public class DepartmentController {
    @Autowired
    private IDepartmentService iDepartmentService;

    /**
     * 部门列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param status      状态
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "查询部门列表")
    @GetMapping("/departments")
    public ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage") Integer currentPage,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted) {
        return iDepartmentService.getDepartmentList(pageSize, currentPage, status, searchValue, sorted);
    }

    /**
     * 新增部门
     *
     * @param departmentRequest 部门信息
     * @return ResultResponse
     */
    @ApiOperation(value = "部门新增")
    @GetMapping("/department/add")
    public ResultResponse addUser(@RequestBody DepartmentRequest departmentRequest) {

        return iDepartmentService.addDepartment(departmentRequest);
    }

    /**
     * 通过Id查询部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "部门详情")
    @GetMapping("/department/{id}")
    public ResultResponse queryUser(@PathVariable String id) {
        return iDepartmentService.queryDepartment(id);
    }

    /**
     * 删除部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/department/{id}")
    public ResultResponse deleteUser(@PathVariable String id) {
        return iDepartmentService.deleteDepartment(id);
    }
}
