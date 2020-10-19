package com.chintec.ikks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.SupplierField;
import com.chintec.ikks.common.entity.po.SupplierFieldPo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.mapper.SupplierFieldMapper;
import com.chintec.ikks.service.ISupplierFieldService;
import io.netty.util.AsciiString;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResultResponse saveField(SupplierFieldPo supplierFieldPo) {
        SupplierField supplierField = new SupplierField();
        BeanUtils.copyProperties(supplierFieldPo, supplierField);
        supplierField.setCreateTime(LocalDateTime.now());
        if (StringUtils.isEmpty(supplierField.getIsDeleted())) {
            supplierField.setIsDeleted(1);
        }
        checkField(supplierFieldPo, supplierField);
        return ResultResponse.successResponse("保存成功");
    }

    @Override
    public ResultResponse updateField(SupplierFieldPo supplierFieldPo) {
        SupplierField supplierField = new SupplierField();
        AssertsUtil.isTrue(!StringUtils.isEmpty(supplierField.getId()), "请选择要修改的属性");
        AssertsUtil.isTrue(this.getById(supplierFieldPo.getId()) == null, "要修改的属性类型不存在");
        checkField(supplierFieldPo, supplierField);
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
        PageResultResponse<SupplierField> pageResultResponse = new PageResultResponse<>(page.getPages(), currentPage, pageSize);
        pageResultResponse.setTotalRecords(page.getTotal());
        pageResultResponse.setResults(page.getRecords());
        return ResultResponse.successResponse(pageResultResponse);
    }

    private void checkField(SupplierFieldPo supplierFieldPo, SupplierField supplierField) {
        AssertsUtil.isTrue(!CollectionUtils.isEmpty(this.list(new QueryWrapper<SupplierField>().lambda()
                .eq(SupplierField::getFieldName, supplierFieldPo.getFieldName())
                .eq(SupplierField::getIsDeleted, 1))), "字段中文名字已经存在");
        AssertsUtil.isTrue(!CollectionUtils.isEmpty(this.list(new QueryWrapper<SupplierField>().lambda()
                .eq(SupplierField::getField, supplierFieldPo.getField())
                .eq(SupplierField::getIsDeleted, 1))), "字段英文名字已经存在");
        BeanUtils.copyProperties(supplierFieldPo, supplierField);
        supplierField.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!this.saveOrUpdate(supplierField), "更新或修改属性类型失败");
    }
}
