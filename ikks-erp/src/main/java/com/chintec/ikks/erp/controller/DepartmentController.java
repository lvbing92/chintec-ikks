package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.vo.DepartmentRequest;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import com.chintec.ikks.erp.feign.IDepartmentService;
import com.chintec.ikks.erp.service.IProcessAndControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/2 9:50
 */
@RestController
@RequestMapping(value = "v1")
@Slf4j
@Api(value = "auth", tags = "部门管理")
public class DepartmentController {
    @Autowired
    private IDepartmentService iDepartmentService;
    @Autowired
    private IProcessAndControllerService iProcessAndControllerService;

    /**
     * 部门列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "查询部门列表")
    @GetMapping("/departments")
    @PermissionAnnotation(code ="201001")
    public ResultResponse getDepartmentList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                            @RequestParam(value = "currentPage") Integer currentPage,
                                            @RequestParam(value = "searchValue", required = false) String searchValue,
                                            @RequestParam(value = "sorted", required = false) String sorted) {
        return iDepartmentService.getDepartmentList(pageSize, currentPage, searchValue, sorted);
    }

    /**
     * 新增部门
     *
     * @param departmentRequest 部门信息
     * @return ResultResponse
     */
    @ApiOperation(value = "部门新增")
    @PostMapping("/department/add")
    @PermissionAnnotation(code ="201002")
    public ResultResponse addDepartment(DepartmentRequest departmentRequest) {

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
    @PermissionAnnotation(code ="201003")
    public ResultResponse queryDepartment(@PathVariable Integer id) {
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
    @PermissionAnnotation(code ="201004")
    public ResultResponse deleteDepartment(@PathVariable Integer id) {
        return iDepartmentService.deleteDepartment(id);
    }

    @GetMapping("/department/tasks")
    @ApiOperation("部门---我的审核任务")
    @PermissionAnnotation(code ="201009")
    public ResultResponse tasks(@RequestParam Integer currentPage,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false, defaultValue = "0") Integer statusId,
                                @RequestParam(required = false) String params, HttpServletRequest request) {
        log.info("department tasks:{}", pageSize);
        return iProcessAndControllerService.taskStatus(request.getHeader("access_token"), currentPage, pageSize, statusId, params);
    }
}
