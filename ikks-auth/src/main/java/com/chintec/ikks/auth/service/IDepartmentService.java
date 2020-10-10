package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.auth.entity.Department;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-02
 */
public interface IDepartmentService extends IService<Department> {
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
    ResultResponse getDepartmentList(Integer pageSize, Integer currentPage,
                                     String status, String searchValue, String sorted);

    /**
     * 新增部门
     *
     * @param department 部门对象
     * @return ResultResponse
     */
    ResultResponse addDepartment(Department department);

    /**
     * 通过Id查询部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    ResultResponse queryDepartment(String id);

    /**
     * 删除部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    ResultResponse deleteDepartment(String id);
}
