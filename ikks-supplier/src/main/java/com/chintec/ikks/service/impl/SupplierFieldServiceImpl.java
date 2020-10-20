package com.chintec.ikks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.SupplierField;
import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.mapper.SupplierFieldMapper;
import com.chintec.ikks.service.ISupplierFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 供应商属性字段表 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Service
public class SupplierFieldServiceImpl extends ServiceImpl<SupplierFieldMapper, SupplierField> implements ISupplierFieldService {

    @Override
    public ResultResponse saveField(SupplierFieldVo supplierFieldVo) {
        SupplierField supplierField = new SupplierField();
        BeanUtils.copyProperties(supplierFieldVo, supplierField);
        supplierField.setCreateTime(LocalDateTime.now(ZoneOffset.UTC));
        if (StringUtils.isEmpty(supplierField.getIsDeleted())) {
            supplierField.setIsDeleted(1);
        }
        checkField(supplierFieldVo, supplierField);
        return ResultResponse.successResponse("保存成功");
    }

    @Override
    public ResultResponse updateField(SupplierFieldVo supplierFieldVo) {
        SupplierField supplierField = new SupplierField();
        AssertsUtil.isTrue(!StringUtils.isEmpty(supplierField.getId()), "请选择要修改的属性");
        AssertsUtil.isTrue(this.getById(supplierFieldVo.getId()) == null, "要修改的属性类型不存在");
        checkField(supplierFieldVo, supplierField);
        return ResultResponse.successResponse("修改成功");
    }

    @Override
    public ResultResponse deleteField(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要禁用或启用的字段");
        SupplierField byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要禁用/启用的字段不存在");
        assert byId != null;
        if (byId.getIsDeleted() == 1) {
            byId.setIsDeleted(0);
        } else {
            byId.setIsDeleted(1);
        }
        byId.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!this.saveOrUpdate(byId), "禁用/启用字段失败");
        return ResultResponse.successResponse("禁用/启用成功");
    }

    @Override
    public ResultResponse fields(Integer currentPage, Integer pageSize) {
        IPage<SupplierField> page = this.page(new Page<>(currentPage, pageSize), new QueryWrapper<SupplierField>()
                .lambda()
                .eq(SupplierField::getIsDeleted, 1)
                .orderByDesc(SupplierField::getCreateTime));
        PageResultResponse<SupplierField> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(page.getPages());
        pageResultResponse.setResults(page.getRecords());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse field(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要查询的字段");
        SupplierField byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要查询的内容不存在");
        return ResultResponse.successResponse(byId);
    }

    private void checkField(SupplierFieldVo supplierFieldVo, SupplierField supplierField) {
        AssertsUtil.isTrue(!CollectionUtils.isEmpty(this.list(new QueryWrapper<SupplierField>().lambda()
                .eq(SupplierField::getFieldName, supplierFieldVo.getFieldName())
                .eq(SupplierField::getIsDeleted, 1))), "字段中文名字已经存在");
        AssertsUtil.isTrue(!CollectionUtils.isEmpty(this.list(new QueryWrapper<SupplierField>().lambda()
                .eq(SupplierField::getField, supplierFieldVo.getField())
                .eq(SupplierField::getIsDeleted, 1))), "字段英文名字已经存在");
        BeanUtils.copyProperties(supplierFieldVo, supplierField);
        supplierField.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
        AssertsUtil.isTrue(!this.saveOrUpdate(supplierField), "更新或修改属性类型失败");
    }
}
