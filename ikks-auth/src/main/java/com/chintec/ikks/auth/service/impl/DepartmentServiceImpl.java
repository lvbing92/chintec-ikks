package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.DepartmentMapper;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.ICompanyUserService;
import com.chintec.ikks.auth.service.IDepartmentService;
import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.CompanyUser;
import com.chintec.ikks.common.entity.Department;
import com.chintec.ikks.common.entity.response.DepartmentResponse;
import com.chintec.ikks.common.entity.vo.DepartmentRequest;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements
        IDepartmentService {

    @Autowired
    private IAuthorityService iAuthorityService;
    @Autowired
    private ICompanyUserService iCompanyUserService;
    private static final String SORT_D = "D";

    @Override
    public ResultResponse getDepartmentList(Integer pageSize, Integer currentPage, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_D;
        }
        //
        IPage<Department> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Department> departmentPage = this.page(page, new QueryWrapper<Department>().lambda()
                .eq(Department::getEnabled, 1)
                .and(!StringUtils.isEmpty(searchValue), s -> s.like(Department::getId, searchValue).
                        or().like(Department::getName, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Credentials::getName)
                .orderByDesc(SORT_D.equals(sorted), Department::getCreateTime));
        List<DepartmentResponse> departmentList = null;
        if (departmentPage.getRecords().size() != 0) {
            departmentList = departmentPage.getRecords().stream().map(department -> {
                DepartmentResponse departmentResponse = new DepartmentResponse();
                BeanUtils.copyProperties(department, departmentResponse);
                //查询
                int count = iCompanyUserService.count(new QueryWrapper<CompanyUser>().lambda()
                        .eq(CompanyUser::getDepartmentId, department.getId()));
                departmentResponse.setCountCompanyUser(count);
                return departmentResponse;
            }).collect(Collectors.toList());
            //返回结果
            PageResultResponse<DepartmentResponse> paginationResultDto = new PageResultResponse<>(departmentPage.getTotal(), currentPage, pageSize);
            paginationResultDto.setTotalPages(departmentPage.getPages());
            paginationResultDto.setResults(departmentList);
            return ResultResponse.successResponse(paginationResultDto);
        } else {
            return ResultResponse.successResponse(new PageResultResponse<>(0L, currentPage, pageSize));
        }


    }

    /**
     * 新增部门
     *
     * @param departmentRequest 部门对象
     * @return ResultResponse
     */
    @Override
    public ResultResponse addDepartment(DepartmentRequest departmentRequest) {
        if (StringUtils.isEmpty(departmentRequest.getId())) {
            //查询当前部门是否已存在
            Department isHave = this.getOne(new QueryWrapper<Department>().lambda().
                    eq(Department::getName, departmentRequest.getName()));
            AssertsUtil.isTrue(!ObjectUtils.isEmpty(isHave), "当前部门已存在");
            Department department = new Department();
            department.setName(departmentRequest.getName());
            department.setIsDefault(departmentRequest.getIsDefault());
            department.setEnabled(true);
            department.setCreateTime(LocalDateTime.now());
            this.save(department);
        } else {
            //g根据Id查询当前部门信息
            Department currentDepartment = this.getById(departmentRequest.getId());
            currentDepartment.setUpdateTime(LocalDateTime.now());
            currentDepartment.setName(departmentRequest.getName());
            this.updateById(currentDepartment);
        }
        return ResultResponse.successResponse("保存成功！");
    }

    /**
     * 通过Id查询部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse queryDepartment(Integer id) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        //查询部门信息
        Department department = this.getById(id);
        BeanUtils.copyProperties(department, departmentResponse);
        //查询角色信息
        List<Authority> allRoleList = iAuthorityService.getAllRoleList();
        departmentResponse.setAuthorities(allRoleList);
        return ResultResponse.successResponse("查询部门详情成功", departmentResponse);
    }

    /**
     * 删除部门
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse deleteDepartment(Integer id) {
        //查询用户
        Department department = this.getById(id);
        department.setEnabled(false);
        boolean flag = this.saveOrUpdate(department);
        if (!flag) {
            return ResultResponse.failResponse("删除失败！");
        }
        return ResultResponse.successResponse("删除部门成功");
    }
}
