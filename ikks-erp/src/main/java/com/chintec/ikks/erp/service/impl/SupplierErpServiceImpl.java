package com.chintec.ikks.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.Supplier;
import com.chintec.ikks.common.entity.SupplierField;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.response.SupplierFieldResponse;
import com.chintec.ikks.common.entity.response.SupplierResponse;
import com.chintec.ikks.common.entity.response.SupplierTypeResponse;
import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.common.util.TimeUtils;
import com.chintec.ikks.erp.feign.ISupplierService;
import com.chintec.ikks.erp.service.ISupplierErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/20 9:55
 */
@Service
@Slf4j
public class SupplierErpServiceImpl implements ISupplierErpService {
    @Autowired
    private ISupplierService iSupplierService;

    @Override
    public ResultResponse saveField(SupplierFieldVo supplierFieldVo) {
        return iSupplierService.saveField(supplierFieldVo);
    }

    @Override
    public ResultResponse updateField(SupplierFieldVo supplierFieldVo) {
        return iSupplierService.updateField(supplierFieldVo);
    }

    @Override
    public ResultResponse deleteField(Integer id) {
        return iSupplierService.deleteField(id);
    }

    @Override
    public ResultResponse fields(Integer currentPage, Integer pageSize) {
        ResultResponse fields = iSupplierService.fields(currentPage, pageSize);
        if (!fields.isSuccess()) {
            return fields;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(fields.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), SupplierField.class).stream().map(supplierField -> {
            SupplierFieldResponse supplierFieldResponse = new SupplierFieldResponse();
            BeanUtils.copyProperties(supplierField, supplierFieldResponse);
            supplierFieldResponse.setCreateTime(TimeUtils.toTimeStamp(supplierField.getCreateTime()));
            supplierFieldResponse.setUpdateTime(TimeUtils.toTimeStamp(supplierField.getUpdateTime()));
            return supplierFieldResponse;
        }).collect(Collectors.toList()));
        return ResultResponse.successResponse("查询成功", pageResultResponse);
    }

    @Override
    public ResultResponse field(Integer id) {
        ResultResponse field = iSupplierService.field(id);
        AssertsUtil.isTrue(!field.isSuccess(), field.getMessage());
        SupplierField supplierField = JSONObject.parseObject(JSONObject.toJSONString(field.getData()), SupplierField.class);
        SupplierFieldResponse supplierFieldResponse = new SupplierFieldResponse();
        BeanUtils.copyProperties(field, supplierFieldResponse);
        supplierFieldResponse.setCreateTime(TimeUtils.toTimeStamp(supplierField.getCreateTime()));
        supplierFieldResponse.setUpdateTime(TimeUtils.toTimeStamp(supplierField.getUpdateTime()));
        return ResultResponse.successResponse(supplierFieldResponse);
    }

    @Override
    public ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, Integer statusId, String params) {
        ResultResponse suppliers = iSupplierService.suppliers(currentPage, pageSize, categoryId, statusId, params);
        if (!suppliers.isSuccess()) {
            return suppliers;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(suppliers.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), Supplier.class).stream().map(supplier -> {
            SupplierResponse supplierResponse = new SupplierResponse();
            BeanUtils.copyProperties(supplier, supplierResponse);
            supplierResponse.setCreateTime(TimeUtils.toTimeStamp(supplier.getCreateTime()));
            supplierResponse.setUpdateTime(TimeUtils.toTimeStamp(supplier.getUpdateTime()));
            supplierResponse.setComCreateDate(TimeUtils.toTimeStamp(supplier.getComCreateDate()));
            return supplierResponse;
        }).collect(Collectors.toList()));
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse supplierCount() {
        return iSupplierService.supplierCount();
    }

    @Override
    public ResultResponse saveSupplier(SupplierVo supplierVo) {
        return iSupplierService.saveSupplier(supplierVo);
    }

    @Override
    public ResultResponse updateSupplier(SupplierVo supplierVo) {
        return iSupplierService.updateSupplier(supplierVo);
    }

    @Override
    public ResultResponse deleteSupplier(Integer id) {
        return iSupplierService.deleteSupplier(id);
    }

    @Override
    public ResultResponse supplier(Integer id) {
        ResultResponse resultResponse = iSupplierService.supplier(id);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        Supplier supplier = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), Supplier.class);
        SupplierResponse supplierResponse = new SupplierResponse();
        BeanUtils.copyProperties(supplier, supplierResponse);
        supplierResponse.setCreateTime(TimeUtils.toTimeStamp(supplier.getCreateTime()));
        supplierResponse.setUpdateTime(TimeUtils.toTimeStamp(supplier.getUpdateTime()));
        supplierResponse.setComCreateDate(TimeUtils.toTimeStamp(supplier.getComCreateDate()));
        return ResultResponse.successResponse(supplierResponse);
    }

    @Override
    public ResultResponse types(Integer currentPage, Integer pageSize) {
        ResultResponse resultResponse = iSupplierService.types(currentPage, pageSize);
        if (!resultResponse.isSuccess()) {
            return resultResponse;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), SupplierType.class).stream().map(supplierType -> {
            SupplierTypeResponse supplierTypeResponse = new SupplierTypeResponse();
            BeanUtils.copyProperties(supplierType, supplierTypeResponse);
            supplierTypeResponse.setCreateTime(TimeUtils.toTimeStamp(supplierType.getCreateTime()));
            supplierTypeResponse.setUpdateTime(TimeUtils.toTimeStamp(supplierType.getUpdateTime()));
            return supplierTypeResponse;
        }).collect(Collectors.toList()));
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse saveType(SupplierTypeVo supplierTypeVo) {
        return iSupplierService.saveType(supplierTypeVo);
    }

    @Override
    public ResultResponse updateType(SupplierTypeVo supplierTypeVo) {
        return iSupplierService.updateType(supplierTypeVo);
    }

    @Override
    public ResultResponse deleteType(Integer id) {
        return iSupplierService.deleteSupplier(id);
    }

    @Override
    public ResultResponse type(Integer id) {
        ResultResponse typeResultResponse = iSupplierService.type(id);
        AssertsUtil.isTrue(!typeResultResponse.isSuccess(), typeResultResponse.getMessage());
        SupplierType supplierType = JSONObject.parseObject(JSONObject.toJSONString(typeResultResponse.getData()), SupplierType.class);
        SupplierTypeResponse supplierTypeResponse = new SupplierTypeResponse();
        BeanUtils.copyProperties(supplierType, supplierTypeResponse);
        supplierTypeResponse.setCreateTime(TimeUtils.toTimeStamp(supplierType.getCreateTime()));
        supplierTypeResponse.setUpdateTime(TimeUtils.toTimeStamp(supplierType.getUpdateTime()));
        return ResultResponse.successResponse(supplierTypeResponse);
    }
}
