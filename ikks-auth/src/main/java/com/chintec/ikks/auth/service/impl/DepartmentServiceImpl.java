package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Department;
import com.chintec.ikks.auth.mapper.DepartmentMapper;
import com.chintec.ikks.auth.service.IDepartmentService;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public ResultResponse getDepartmentList(Integer pageSize, Integer currentPage, String status, String searchValue, String sorted) {
        return null;
    }

    @Override
    public ResultResponse addDepartment(Department department) {
        return null;
    }

    @Override
    public ResultResponse queryDepartment(String id) {
        //查询用户
        Department department =this.getById(new QueryWrapper<Department>().lambda().eq(Department::getId,id));
        //查询当前用户
        return ResultResponse.successResponse("查询部门详情成功",department);
    }

    @Override
    public ResultResponse deleteDepartment(String id) {
        this.baseMapper.deleteById(new QueryWrapper<Department>().lambda().eq(Department::getId,id));
        return ResultResponse.successResponse("删除部门成功");
    }
}
