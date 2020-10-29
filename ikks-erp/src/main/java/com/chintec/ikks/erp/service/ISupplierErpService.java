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
    ResultResponse saveField(SupplierFieldVo supplierFieldVo, String token);


    ResultResponse updateField(SupplierFieldVo supplierFieldVo, String token);


    ResultResponse deleteField(Integer id);


    ResultResponse fields(Integer currentPage, Integer pageSize);

    ResultResponse field(Integer id);


    ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, Integer statusId, String params, String tokens);


    ResultResponse supplierCount();


    ResultResponse saveSupplier(SupplierVo supplierVo, String token);


    ResultResponse updateSupplier(SupplierVo supplierVo, String token);


    ResultResponse deleteSupplier(Integer id);


    ResultResponse supplier(Integer id, Integer qualificationId);


    ResultResponse types(Integer currentPage, Integer pageSize);


    ResultResponse saveType(SupplierTypeVo supplierTypeVo, String token);


    ResultResponse updateType(SupplierTypeVo supplierTypeVo, String token);


    ResultResponse deleteType(Integer id);


    ResultResponse type(Integer id);


}
