package com.chintec.ikks.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.Qualification;
import com.chintec.ikks.common.entity.QualificationSupplier;
import com.chintec.ikks.common.entity.po.SupplierFunctionPo;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.entity.response.QualificationResponse;
import com.chintec.ikks.common.entity.response.QualificationSupplierResponse;
import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.common.util.TimeUtils;
import com.chintec.ikks.erp.feign.IFlowTaskService;
import com.chintec.ikks.erp.feign.IQualificationService;
import com.chintec.ikks.erp.feign.IQualificationSupplierService;
import com.chintec.ikks.erp.service.IQualificationAndProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/21 10:19
 */
@Service
public class QualificationAndProcessServiceImpl implements IQualificationAndProcessService {
    @Autowired
    private IQualificationService iQualificationService;
    @Autowired
    private IQualificationSupplierService iQualificationSupplierService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultResponse saveQualification(QualificationVo qualificationVo, String token) {
        return iQualificationService.saveQualification((QualificationVo) getUser(redisTemplate, token, qualificationVo, null));
    }

    @Override
    public ResultResponse updateQualification(QualificationVo qualificationVo, String token) {
        return iQualificationService.updateQualification((QualificationVo) getUser(redisTemplate, token, qualificationVo, null));
    }

    @Override
    public ResultResponse qualifications(Integer currentPage, Integer pageSize, Integer categoryId, String token) {
        ResultResponse qualifications = iQualificationService.qualifications(currentPage, pageSize, categoryId);
        if (!qualifications.isSuccess()) {
            return qualifications;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(qualifications.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), Qualification.class)
                .stream()
                .map(s -> getQualificationResponse(s, token))
                .collect(Collectors.toList()));
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse qualification(Integer id, String token) {
        ResultResponse resultResponse = iQualificationService.qualification(id);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage(), resultResponse.getCode());
        Qualification qualification = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), Qualification.class);
        return ResultResponse.successResponse(getQualificationResponse(qualification, token));
    }

    @Override
    public ResultResponse deleteQualification(Integer id) {
        return iQualificationService.deleteQualification(id);
    }

    @Override
    public ResultResponse saveQualificationSupplier(String qualificationSupplierVo, String token) {
        AssertsUtil.isTrue(StringUtils.isEmpty(qualificationSupplierVo), "请传入数据");
        return iQualificationSupplierService.saveSupplierQualification(JSONObject.parseArray(qualificationSupplierVo, QualificationSupplierVo.class).stream().map(s -> (QualificationSupplierVo) getUser(redisTemplate, token, null, s)).collect(Collectors.toList()));
    }

    @Override
    public ResultResponse updateQualificationSupplier(String qualificationSupplierVo, String token) {
        AssertsUtil.isTrue(StringUtils.isEmpty(qualificationSupplierVo), "请传入数据");
        return iQualificationSupplierService.updateSupplierQualification(JSONObject.parseArray(qualificationSupplierVo, QualificationSupplierVo.class).stream().map(s -> (QualificationSupplierVo) getUser(redisTemplate, token, null, s)).collect(Collectors.toList()));
    }

    @Override
    public ResultResponse deleteQualificationSupplier(Integer id) {
        return iQualificationSupplierService.deleteSupplierQualification(id);
    }

    @Override
    public ResultResponse qualificationSupplier(Integer id) {
        return iQualificationSupplierService.supplierQualification(id);
    }

    @Override
    public ResultResponse qualificationSuppliers(Integer qualificationSupplierId, Integer supplierId) {
        ResultResponse resultResponse = iQualificationSupplierService.supplierQualifications(qualificationSupplierId, supplierId);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        List<QualificationSupplierResponse> collect = JSONObject.parseArray(JSONObject.toJSONString(resultResponse.getData()), QualificationSupplier.class)
                .stream()
                .map(qualificationSupplier -> {
                    QualificationSupplierResponse qualificationSupplierResponse = new QualificationSupplierResponse();
                    BeanUtils.copyProperties(qualificationSupplier, qualificationSupplierResponse);
                    return qualificationSupplierResponse;
                }).collect(Collectors.toList());
        return ResultResponse.successResponse(collect);
    }

    private QualificationResponse getQualificationResponse(Qualification qualification, String token) {
        QualificationResponse qualificationResponse = new QualificationResponse();
        BeanUtils.copyProperties(qualification, qualificationResponse);
        CredentialsResponse user = (CredentialsResponse) getUser(redisTemplate, token, null, null);
        if ("3".equals(user.getLevel())) {
            //todo
            ResultResponse resultResponse = iQualificationSupplierService.totalSupplierQualification(qualification.getId(), null);
            AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
            qualificationResponse.setCount(JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), SupplierFunctionPo.class).getCount());
        }
        qualificationResponse.setUpdateTime(TimeUtils.toTimeStamp(qualification.getUpdateTime()));
        qualificationResponse.setCreateTime(TimeUtils.toTimeStamp(qualification.getCreateTime()));
        return qualificationResponse;
    }

    private Object getUser(RedisTemplate<String, Object> redisTemplate, String token, QualificationVo qualificationVo, QualificationSupplierVo qualificationSupplierVo) {
        Object o = redisTemplate.opsForHash().get(token, "userMsg");
        AssertsUtil.isTrue(o == null, "请登录");
        CredentialsResponse credentials = JSONObject.parseObject(JSONObject.toJSONString(o), CredentialsResponse.class);
        if (qualificationVo != null) {
            qualificationVo.setUpdateBy(Integer.valueOf(String.valueOf(credentials.getId())));
            qualificationVo.setUpdateName(credentials.getName());
            return qualificationVo;
        } else if (qualificationSupplierVo != null) {
            qualificationSupplierVo.setUpdateBy(Integer.valueOf(String.valueOf(credentials.getId())));
            qualificationSupplierVo.setUpdateName(credentials.getName());
            return qualificationSupplierVo;
        }
        return credentials;
    }

}
