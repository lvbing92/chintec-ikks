package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.DepartmentRequest;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-02
 */
@FeignClient(value = "ikks-auth", path = "v1")
public interface IDepartmentService {
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
    ResultResponse getDepartmentList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "currentPage") Integer currentPage,
                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                     @RequestParam(value = "sorted", required = false) String sorted);

    /**
     * 新增部门
     *
     * @param departmentRequest 部门信息
     * @return ResultResponse
     */
    @ApiOperation(value = "部门新增")
    @PostMapping("/department/add")
    ResultResponse addDepartment(@RequestBody DepartmentRequest departmentRequest);

    /**
     * 通过Id查询部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "部门详情")
    @GetMapping("/department/{id}")
    ResultResponse queryDepartment(@PathVariable Integer id);

    /**
     * 删除部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/department/{id}")
    ResultResponse deleteDepartment(@PathVariable Integer id);

}
