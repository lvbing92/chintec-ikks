package com.chintec.ikks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.QualificationSupplier;
import com.chintec.ikks.common.entity.po.SupplierFunctionPo;
import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.mapper.QualificationSupplierMapper;
import com.chintec.ikks.service.IQualificationSupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@Service
public class QualificationSupplierServiceImpl extends ServiceImpl<QualificationSupplierMapper, QualificationSupplier> implements IQualificationSupplierService {
    @Override
    public ResultResponse saveSupplierQualification(List<QualificationSupplierVo> qualificationSupplierVos) {
        List<QualificationSupplier> collect = qualificationSupplierVos.stream().map(qualificationSupplierVo -> {
            QualificationSupplier qualificationSupplier = new QualificationSupplier();
            BeanUtils.copyProperties(qualificationSupplierVo, qualificationSupplier);
            qualificationSupplier.setCreateTime(LocalDateTime.now(ZoneOffset.UTC));
            qualificationSupplier.setIsDeleted(1);
            qualificationSupplier.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
            return qualificationSupplier;
        }).collect(Collectors.toList());
        AssertsUtil.isTrue(!this.saveOrUpdateBatch(collect), "操作失败");
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse updateSupplierQualification(List<QualificationSupplierVo> qualificationSupplierVos) {
        List<QualificationSupplier> collect = qualificationSupplierVos.stream().map(qualificationSupplierVo -> {
            AssertsUtil.isTrue(StringUtils.isEmpty(qualificationSupplierVo.getId()), "请选择要修改的内容");
            QualificationSupplier byId = this.getById(qualificationSupplierVo.getId());
            AssertsUtil.isTrue(byId == null, "要修改的内容不存在");
            assert byId != null;
            BeanUtils.copyProperties(qualificationSupplierVo, byId);
            byId.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
            return byId;
        }).collect(Collectors.toList());
        AssertsUtil.isTrue(!this.saveOrUpdateBatch(collect), "操作失败");
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse deleteSupplierQualification(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要删除的内容");
        QualificationSupplier byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要删除的内容不存在");
        assert byId != null;
        byId.setIsDeleted(0);
        AssertsUtil.isTrue(!this.saveOrUpdate(byId), "操作失败");
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse supplierQualifications(Integer qualificationId, Integer supplierId) {
        return ResultResponse.successResponse(this.list(new QueryWrapper<QualificationSupplier>()
                .lambda()
                .eq(!StringUtils.isEmpty(qualificationId), QualificationSupplier::getQualificationId, qualificationId)
                .eq(QualificationSupplier::getSupplierId, supplierId)
                .eq(QualificationSupplier::getIsDeleted, 1)
                .orderByDesc(QualificationSupplier::getCreateTime)));
    }

    @Override
    public ResultResponse supplierQualification(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择查看的内容");
        return ResultResponse.successResponse(this.getById(id));
    }

    @Override
    public ResultResponse totalSupplierQualification(Integer qualificationId, Integer supplierId) {
        int count = this.count(new QueryWrapper<QualificationSupplier>().lambda()
                .eq(QualificationSupplier::getQualificationId, qualificationId)
                .eq(QualificationSupplier::getSupplierId, supplierId)
                .eq(QualificationSupplier::getIsDeleted, 1));
        SupplierFunctionPo supplierFunctionPo = new SupplierFunctionPo();
        supplierFunctionPo.setId(count);
        return ResultResponse.successResponse(supplierFunctionPo);
    }

}
