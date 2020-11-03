package com.chintec.ikks.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.mapper.SupplierTypeMapper;
import com.chintec.ikks.service.ISupplierTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * 供应商类型 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Service
public class SupplierTypeServiceImpl extends ServiceImpl<SupplierTypeMapper, SupplierType> implements ISupplierTypeService {

    @Override
    public ResultResponse types(Integer currentPage, Integer pageSiz, String ids) {
        IPage<SupplierType> page = this.page(new Page<>(currentPage, pageSiz), new QueryWrapper<SupplierType>()
                .lambda()
                .in(!StringUtils.isEmpty(ids), SupplierType::getFlowId, JSONObject.parseArray(ids, Integer.class))
                .eq(SupplierType::getIsDeleted, 1)
                .orderByDesc(SupplierType::getCreateTime));
        PageResultResponse<SupplierType> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSiz);
        pageResultResponse.setResults(page.getRecords());
        pageResultResponse.setTotalPages(page.getPages());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse saveType(SupplierTypeVo supplierTypeVo) {
        SupplierType supplierType = new SupplierType();
        BeanUtils.copyProperties(supplierTypeVo, supplierType);
        supplierType.setCreateTime(LocalDateTime.now(ZoneOffset.UTC));
        supplierType.setIsDeleted(1);
        supplierType.setTypeCode(ThreadLocalRandom.current().nextInt());
        saveAndUpdate(supplierType);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse updateType(SupplierTypeVo supplierTypeVo) {
        AssertsUtil.isTrue(StringUtils.isEmpty(supplierTypeVo.getId()), "请选择要修改的内容");
        SupplierType byId = this.getById(supplierTypeVo.getId());
        AssertsUtil.isTrue(byId == null, "要修改的内容不存在");
        assert byId != null;
        BeanUtils.copyProperties(supplierTypeVo, byId);
        saveAndUpdate(byId);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse deleteType(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要删除的内容");
        SupplierType byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要删除的内容不存在");
        assert byId != null;
        byId.setIsDeleted(0);
        saveAndUpdate(byId);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse type(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要查询内容");
        SupplierType byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要查询的内容不存在");
        return ResultResponse.successResponse(byId);
    }

    private void saveAndUpdate(SupplierType supplierType) {
        supplierType.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
        AssertsUtil.isTrue(!this.saveOrUpdate(supplierType), "操作失败");
    }
}
