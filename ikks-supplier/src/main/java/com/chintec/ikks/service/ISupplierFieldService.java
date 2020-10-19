package com.chintec.ikks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.SupplierField;
import com.chintec.ikks.common.entity.po.SupplierFieldPo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 供应商属性字段表 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
public interface ISupplierFieldService extends IService<SupplierField> {
    /**
     * 保存添加扩展字段
     *
     * @param supplierFieldPo 字段属性
     * @return resultResponse
     */
    ResultResponse saveField(SupplierFieldPo supplierFieldPo);

    /**
     * 修改自定属性
     *
     * @param supplierFieldPo 字段属性类
     * @return resultResponse
     */
    ResultResponse updateField(SupplierFieldPo supplierFieldPo);

    /**
     * 启用禁用自定义字段
     *
     * @param id 字段属性id
     * @return resultResponse
     */
    ResultResponse deleteField(Integer id);

    /**
     * 查询该分类的所有字段属性
     * @return
     */
    ResultResponse fields(Integer currentPage,Integer pageSize);

}
