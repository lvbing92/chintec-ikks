package com.chintec.ikks.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.Credentials;
import com.chintec.ikks.common.entity.Supplier;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.po.SupplierFunctionPo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.feign.ICredentialsService;
import com.chintec.ikks.mapper.SupplierMapper;
import com.chintec.ikks.service.ISupplierService;
import com.chintec.ikks.service.ISupplierTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {
    @Autowired
    private ISupplierTypeService iSupplierTypeService;
    @Autowired
    private ICredentialsService iCredentialsService;

    @Override
    public ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, String params, Integer statusId, String ids) {

        IPage<Supplier> page = this.page(new Page<>(currentPage, pageSize), new QueryWrapper<Supplier>()
                .lambda()
                .eq(categoryId != 0, Supplier::getCategoryId, categoryId)
                .eq(statusId != 0, Supplier::getIsAuthenticated, statusId)
                .eq(Supplier::getIsDeleted, 1)
                .in(!StringUtils.isEmpty(ids), Supplier::getCategoryId, JSONObject.parseArray(ids, Integer.class))
                .apply(!StringUtils.isEmpty(params), "concat(IFNULL(id,''),IFNULL(company_name,''),IFNULL(contact_phone,''),IFNULL(contact_email,'')) like '%" + params + "%'")
                .orderByDesc(Supplier::getUpdateTime));
        PageResultResponse<Supplier> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(page.getPages());
        pageResultResponse.setResults(page.getRecords());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse countTotal() {
        List<SupplierFunctionPo> collect = iSupplierTypeService.list(new QueryWrapper<SupplierType>().lambda().eq(SupplierType::getIsDeleted, 1))
                .stream().map(s -> {
                    SupplierFunctionPo supplierFunctionPo = new SupplierFunctionPo();
                    supplierFunctionPo.setCount(count(s.getId()));
                    supplierFunctionPo.setId(s.getId());
                    supplierFunctionPo.setName(s.getTypeName());
                    return supplierFunctionPo;
                }).collect(Collectors.toList());
        return ResultResponse.successResponse(collect);
    }

    @Override
    @Transactional
    public ResultResponse saveSupplier(SupplierVo supplierVo) {
        Supplier supplier = new Supplier();
        Supplier one = this.getOne(new QueryWrapper<Supplier>().lambda()
                .eq(Supplier::getContactEmail, supplierVo.getContactEmail()).eq(Supplier::getIsDeleted, 1));
        AssertsUtil.isTrue(one != null, "该公司已经存在");
        BeanUtils.copyProperties(supplierVo, supplier);
        supplier.setComCreateDate(LocalDateTime.ofEpochSecond(Long.parseLong(supplierVo.getComCreateDate()), 0, ZoneOffset.UTC));
        supplier.setIsDeleted(1);
        supplier.setIsAuthenticated(1);
        saveAndUpdate(supplier);
        Credentials credentials = new Credentials();
        credentials.setUserId(supplier.getId());
        credentials.setName(supplierVo.getContactEmail());
        credentials.setPassword(supplierVo.getPassword());
        credentials.setUserType("3");
        credentials.setEnabled(true);
        boolean flag = iCredentialsService.addLoginMsg(credentials);
        AssertsUtil.isTrue(!flag, "创建供应商失败");
        return ResultResponse.successResponse("创建供应商成功");
    }

    @Override
    public ResultResponse updateSupplier(SupplierVo supplierVo) {
        AssertsUtil.isTrue(StringUtils.isEmpty(supplierVo.getId()), "请选择要修改的内容");
        Supplier byId = this.getById(supplierVo.getId());
        AssertsUtil.isTrue(byId == null, "要修改的内容不存在");
        assert byId != null;
        BeanUtils.copyProperties(supplierVo, byId);
        byId.setComCreateDate(LocalDateTime.ofEpochSecond(Long.parseLong(supplierVo.getComCreateDate()), 0, ZoneOffset.UTC));
        saveAndUpdate(byId);
        return ResultResponse.successResponse("修改成功");
    }

    @Override
    public ResultResponse deleteSupplier(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要删除的内容");
        Supplier byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要删除的内容不存在");
        assert byId != null;
        byId.setIsDeleted(0);
        saveAndUpdate(byId);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse supplier(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要查询的内容");
        Supplier byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要查询的内容不存在");
        return ResultResponse.successResponse(byId);
    }

    private int count(Integer categoryId) {
        return this.count(new QueryWrapper<Supplier>().lambda()
                .eq(Supplier::getCategoryId, categoryId)
                .eq(Supplier::getIsDeleted, 1));
    }

    private void saveAndUpdate(Supplier supplier) {
        supplier.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
        AssertsUtil.isTrue(!this.saveOrUpdate(supplier), "操作失败");
    }
}
