package com.chintec.ikks.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.Qualification;
import com.chintec.ikks.common.entity.Supplier;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.entity.response.DepartTaskResponse;
import com.chintec.ikks.common.entity.response.FlowTaskStatusResponse;
import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.feign.IFlowInfoService;
import com.chintec.ikks.erp.feign.IFlowTaskService;
import com.chintec.ikks.erp.feign.IQualificationService;
import com.chintec.ikks.erp.feign.ISupplierService;
import com.chintec.ikks.erp.service.IProcessAndControllerService;
import com.chintec.ikks.erp.service.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/21 14:02
 */
@Service
@Slf4j
public class ProcessAndControllerServiceImpl implements IProcessAndControllerService {
    @Autowired
    private IFlowInfoService iFlowInfoService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private ISupplierService iSupplierService;
    @Autowired
    private IQualificationService iQualificationService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultResponse createProcess(FlowInfoVo flowInfoVo) {
        log.info("创建流程:{}", flowInfoVo);
        return iFlowInfoService.createFlowNode(flowInfoVo);
    }

    @Override
    public ResultResponse startProcess(String token, Integer supplierId) {
        log.info("startProcess   客户id:{}", supplierId + "开启流程");
        ResultResponse resultResponse = iSupplierService.supplier(supplierId);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        Supplier supplier = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), Supplier.class);
        FlowTaskVo flowTaskVo = new FlowTaskVo();
        flowTaskVo.setTaskId(supplierId);
        flowTaskVo.setName(supplier.getCompanyName());
        flowTaskVo.setStatus(NodeStateEnum.PENDING.getCode().toString());
        ResultResponse type = iSupplierService.type(supplier.getCategoryId());
        AssertsUtil.isTrue(!type.isSuccess(), type.getMessage());
        SupplierType supplierType = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), SupplierType.class);
        flowTaskVo.setFollowInfoId(supplierType.getFlowId());
        ResultResponse task = iFlowTaskService.createTask(flowTaskVo);
        AssertsUtil.isTrue(!task.isSuccess(), task.getMessage());
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse passFlowNode(String token, Integer flowTaskStatusId, Integer code) {
        log.info("pass:{},token:{},code:{}", flowTaskStatusId, token, code);
        return iFlowTaskService.passFlowNode(flowTaskStatusId, code);
    }

    @Override
    public ResultResponse refuseFlowNode(String token, Integer flowTaskStatusId) {
        log.info("refuse:{}", flowTaskStatusId);
        return iFlowTaskService.refuseFlowNode(flowTaskStatusId);
    }

    @Override
    public ResultResponse taskStatus(String token, Integer currentPage, Integer pageSize, Integer statusId, String params) {
        CredentialsResponse credentialsResponse = UserUtils.getCredentialsResponse(redisTemplate, token);
        ResultResponse resultResponse = iFlowTaskService.flowTaskStatus(Integer.valueOf(String.valueOf(credentialsResponse.getId())), currentPage, pageSize, statusId, params);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(resultResponse), PageResultResponse.class);
        List<DepartTaskResponse> collect = JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), FlowTaskStatusResponse.class)
                .stream()
                .map(s -> {
                    DepartTaskResponse departTaskResponse = new DepartTaskResponse();
                    ResultResponse qualificationResult = iQualificationService.qualification(s.getQualificationId());
                    AssertsUtil.isTrue(!qualificationResult.isSuccess(), qualificationResult.getMessage());
                    Qualification qualification = JSONObject.parseObject(JSONObject.toJSONString(qualificationResult.getData()), Qualification.class);
                    departTaskResponse.setName(qualification.getQualificationName());
                    departTaskResponse.setQualificationId(s.getQualificationId());
                    ResultResponse typeResult = iSupplierService.type(qualification.getCategoryId());
                    AssertsUtil.isTrue(!typeResult.isSuccess(), typeResult.getMessage());
                    SupplierType supplierType = JSONObject.parseObject(JSONObject.toJSONString(typeResult.getData()), SupplierType.class);
                    departTaskResponse.setCategoryName(supplierType.getTypeName());
                    ResultResponse supplierResult = iSupplierService.supplier(s.getTaskId());
                    AssertsUtil.isTrue(!supplierResult.isSuccess(), supplierResult.getMessage());
                    Supplier supplier = JSONObject.parseObject(JSONObject.toJSONString(supplierResult.getData()), Supplier.class);
                    departTaskResponse.setCompanyName(supplier.getCompanyName());
                    departTaskResponse.setNodeId(s.getNodeId());
                    departTaskResponse.setId(s.getId());
                    departTaskResponse.setStatus(s.getStatus());
                    log.info("departTaskResponse:{}", departTaskResponse);
                    return departTaskResponse;
                }).collect(Collectors.toList());
        pageResultResponse.setResults(collect);
        return ResultResponse.successResponse(pageResultResponse);
    }



    @Override
    public ResultResponse update(FlowInfoVo flowInfoVo) {
        return iFlowInfoService.update(flowInfoVo);
    }


    @Override
    public ResultResponse list(Integer currentPage, Integer pageSize) {
        return iFlowInfoService.list(currentPage,pageSize);
    }


    @Override
    public ResultResponse one(Integer id) {
        return iFlowInfoService.one(id);
    }

    @Override
    public ResultResponse delete(Integer id) {
        return iFlowInfoService.delete(id);
    }

}
