package com.chintec.ikks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.QualificationSupplier;
import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.util.ResultResponse;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
public interface IQualificationSupplierService extends IService<QualificationSupplier> {
    /**
     * 保存用户资质文档
     *
     * @param qualificationSupplierVos 资质文档实体集合
     * @return resultResponse
     */
    ResultResponse saveSupplierQualification(List<QualificationSupplierVo> qualificationSupplierVos);

    /**
     * 更新户资质文档
     *
     * @param qualificationSupplierVos 资质文档实体类
     * @return resultResponse
     */
    ResultResponse updateSupplierQualification(List<QualificationSupplierVo> qualificationSupplierVos);

    /**
     * 删除用户资质文档
     *
     * @param id 资质文档id
     * @return resultResponse
     */
    ResultResponse deleteSupplierQualification(Integer id);

    /**
     * 查询用户资质文档列表
     *
     * @param supplierId      商户id
     * @param qualificationId 资质id
     * @return resultResponse
     */
    ResultResponse supplierQualifications(Integer qualificationId, Integer supplierId);

    /**
     * 查询一个资质文档详情
     *
     * @param id 资质文档id
     * @return resultResponse
     */
    ResultResponse supplierQualification(Integer id);

    /**
     * 查询每个资质文档的数量
     *
     * @param qualificationId 文档id
     * @param supplierId      供应商
     * @return resultResponse 结果类
     */
    ResultResponse totalSupplierQualification(Integer qualificationId, Integer supplierId);
}
