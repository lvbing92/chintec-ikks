package com.chintec.ikks.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.Supplier;
import com.chintec.ikks.common.entity.SupplierField;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.response.*;
import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.common.util.TimeUtils;
import com.chintec.ikks.erp.feign.IFlowTaskService;
import com.chintec.ikks.erp.feign.ISupplierService;
import com.chintec.ikks.erp.service.ISupplierErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private QualificationAndProcessServiceImpl qualificationAndProcessService;


    @Override
    public ResultResponse saveField(SupplierFieldVo supplierFieldVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        supplierFieldVo.setUpdateById(Integer.valueOf(String.valueOf(credentialsResponse.getId())));
        supplierFieldVo.setUpdateByName(credentialsResponse.getUpdateByName());
        return iSupplierService.saveField(supplierFieldVo);
    }

    @Override
    public ResultResponse updateField(SupplierFieldVo supplierFieldVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        supplierFieldVo.setUpdateById(Integer.valueOf(String.valueOf(credentialsResponse.getId())));
        supplierFieldVo.setUpdateByName(credentialsResponse.getUpdateByName());
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
        BeanUtils.copyProperties(supplierField, supplierFieldResponse);
        supplierFieldResponse.setCreateTime(TimeUtils.toTimeStamp(supplierField.getCreateTime()));
        supplierFieldResponse.setUpdateTime(TimeUtils.toTimeStamp(supplierField.getUpdateTime()));
        return ResultResponse.successResponse(supplierFieldResponse);
    }

    @Override
    public ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, Integer statusId, String params, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        String ids = null;
        //判断用户所处的级别 是否是部门用户登录
        if ("2".equals(credentialsResponse.getUserType())) {
            ResultResponse tasks = iFlowTaskService.tasks(Integer.valueOf(String.valueOf(credentialsResponse.getId())));
            AssertsUtil.isTrue(!tasks.isSuccess(), tasks.getMessage());
            List<Integer> collect = JSONObject.parseArray(JSONObject.toJSONString(tasks.getData()), FlowTask.class)
                    .stream()
                    .map(FlowTask::getFollowInfoId)
                    .collect(Collectors.toList());
            ids = JSONObject.toJSONString(collect);
            ResultResponse types = iSupplierService.types(1, 1000, ids);
            AssertsUtil.isTrue(!types.isSuccess(), types.getMessage());
            ids = JSONObject.toJSONString(JSONObject.parseArray(JSONObject.toJSONString(types.getData()), SupplierType.class).stream().map(SupplierType::getId).collect(Collectors.toList()));
        }
        ResultResponse suppliers = iSupplierService.suppliers(currentPage, pageSize, categoryId, statusId, params, ids);
        if (!suppliers.isSuccess()) {
            return suppliers;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(suppliers.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), Supplier.class)
                .stream()
                .map(supplier -> {
                    SupplierResponse supplierResponse = new SupplierResponse();
                    BeanUtils.copyProperties(supplier, supplierResponse);
                    supplierResponse.setCreateTime(TimeUtils.toTimeStamp(supplier.getCreateTime()));
                    supplierResponse.setUpdateTime(TimeUtils.toTimeStamp(supplier.getUpdateTime()));
                    supplierResponse.setComCreateDate(TimeUtils.toTimeStamp(supplier.getComCreateDate()));
                    return supplierResponse;
                })
                .collect(Collectors.toList()));
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse supplierCount() {
        return iSupplierService.supplierCount();
    }

    @Override
    public ResultResponse saveSupplier(SupplierVo supplierVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        ResultResponse fields = iSupplierService.fields(1, 100);
        AssertsUtil.isTrue(!fields.isSuccess(), fields.getMessage());
        List<SupplierField> supplierFields = JSONObject.parseArray(JSONObject.toJSONString(JSONObject.parseObject(JSONObject.toJSONString(fields.getData()), PageResultResponse.class).getResults()), SupplierField.class);
        List<Map<String, Object>> collect = supplierFields.stream().map(supplierField -> {
            Map<String, Object> field = new HashMap<>(3);
            field.put(supplierField.getFieldName(), "");
            field.put(supplierField.getField(), "");
            field.put("type", supplierField.getFieldType());
            return field;
        }).collect(Collectors.toList());
        supplierVo.setProperties(JSONObject.toJSONString(collect));
        supplierVo.setUpdateById(Integer.valueOf(String.valueOf(credentialsResponse.getId())));
        supplierVo.setUpdateByName(credentialsResponse.getUpdateByName());
        return iSupplierService.saveSupplier(supplierVo);
    }

    @Override
    public ResultResponse updateSupplier(SupplierVo supplierVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        supplierVo.setUpdateById(Integer.valueOf(String.valueOf(credentialsResponse.getId())));
        supplierVo.setUpdateByName(credentialsResponse.getUpdateByName());
        return iSupplierService.updateSupplier(supplierVo);
    }

    @Override
    public ResultResponse deleteSupplier(Integer id) {
        return iSupplierService.deleteSupplier(id);
    }


    @Override
    public ResultResponse supplier(Integer id, Integer qualificationId) {
        ResultResponse resultResponse = iSupplierService.supplier(id);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        Supplier supplier = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), Supplier.class);
        SupplierResponse supplierResponse = new SupplierResponse();
        BeanUtils.copyProperties(supplier, supplierResponse);
        supplierResponse.setCreateTime(TimeUtils.toTimeStamp(supplier.getCreateTime()));
        supplierResponse.setUpdateTime(TimeUtils.toTimeStamp(supplier.getUpdateTime()));
        supplierResponse.setComCreateDate(TimeUtils.toTimeStamp(supplier.getComCreateDate()));
        ResultResponse supplierQualificationsResultResponse = qualificationAndProcessService.qualificationSuppliers(qualificationId, id);
        AssertsUtil.isTrue(!supplierQualificationsResultResponse.isSuccess(), supplierQualificationsResultResponse.getMessage());
        List<QualificationSupplierResponse> qualificationSuppliers = JSONObject.parseArray(JSONObject.toJSONString(supplierQualificationsResultResponse.getData()), QualificationSupplierResponse.class);
        supplierResponse.setQualificationSupplierResponses(qualificationSuppliers);
        return ResultResponse.successResponse(supplierResponse);
    }

    @Override
    public ResultResponse types(Integer currentPage, Integer pageSize) {
        ResultResponse resultResponse = iSupplierService.types(currentPage, pageSize, null);
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
    public ResultResponse saveType(SupplierTypeVo supplierTypeVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        supplierTypeVo.setUpdateBy(String.valueOf(credentialsResponse.getId()));
        supplierTypeVo.setUpdateName(credentialsResponse.getUpdateByName());

        return iSupplierService.saveType(supplierTypeVo);
    }

    @Override
    public ResultResponse updateType(SupplierTypeVo supplierTypeVo, String token) {
        CredentialsResponse credentialsResponse = getCredentialsResponse(redisTemplate, token);
        supplierTypeVo.setUpdateBy(String.valueOf(credentialsResponse.getId()));
        supplierTypeVo.setUpdateName(credentialsResponse.getUpdateByName());
        return iSupplierService.updateType(supplierTypeVo);
    }

    @Override
    public ResultResponse deleteType(Integer id) {
        return iSupplierService.deleteType(id);
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

    private CredentialsResponse getCredentialsResponse(RedisTemplate<String, Object> redisTemplate, String token) {
        Object o = redisTemplate.opsForHash().get(token, "userMsg");
        AssertsUtil.noLogin(o == null, "请登录");
        return JSONObject.parseObject(JSONObject.toJSONString(o), CredentialsResponse.class);
    }
}
