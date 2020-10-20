package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/20 9:54
 */
public interface ISupplierErpService {
    ResultResponse saveField(SupplierFieldVo supplierFieldVo);


    ResultResponse updateField(SupplierFieldVo supplierFieldVo);


    ResultResponse deleteField(Integer id);


    ResultResponse fields(Integer currentPage, Integer pageSize);

    ResultResponse field(Integer id);


    ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, Integer statusId, String params);


    ResultResponse supplierCount();


    ResultResponse saveSupplier(SupplierVo supplierVo);


    ResultResponse updateSupplier(SupplierVo supplierVo);


    ResultResponse deleteSupplier(Integer id);


    ResultResponse supplier(Integer id);


    ResultResponse types(Integer currentPage, Integer pageSize);


    ResultResponse saveType(SupplierTypeVo supplierTypeVo);


    ResultResponse updateType(SupplierTypeVo supplierTypeVo);


    ResultResponse deleteType(Integer id);


    ResultResponse type(Integer id);


}
