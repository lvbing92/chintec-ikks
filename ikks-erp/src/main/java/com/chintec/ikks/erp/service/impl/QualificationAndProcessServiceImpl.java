package com.chintec.ikks.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.Credentials;
import com.chintec.ikks.common.entity.Qualification;
import com.chintec.ikks.common.entity.response.QualificationResponse;
import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.common.util.TimeUtils;
import com.chintec.ikks.erp.feign.IQualificationService;
import com.chintec.ikks.erp.service.IQualificationAndProcessService;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultResponse saveQualification(QualificationVo qualificationVo, String token) {
        return iQualificationService.saveQualification(getUser(redisTemplate, token, qualificationVo));
    }

    @Override
    public ResultResponse updateQualification(QualificationVo qualificationVo, String token) {
        return iQualificationService.updateQualification(getUser(redisTemplate, token, qualificationVo));
    }

    @Override
    public ResultResponse qualifications(Integer currentPage, Integer pageSize, Integer categoryId) {
        ResultResponse qualifications = iQualificationService.qualifications(currentPage, pageSize, categoryId);
        if (!qualifications.isSuccess()) {
            return qualifications;
        }
        PageResultResponse pageResultResponse = JSONObject.parseObject(JSONObject.toJSONString(qualifications.getData()), PageResultResponse.class);
        pageResultResponse.setResults(JSONObject.parseArray(JSONObject.toJSONString(pageResultResponse.getResults()), Qualification.class)
                .stream()
                .map(this::getQualificationResponse)
                .collect(Collectors.toList()));
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse qualification(Integer id) {
        ResultResponse resultResponse = iQualificationService.qualification(id);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage(), resultResponse.getCode());
        Qualification qualification = JSONObject.parseObject(JSONObject.toJSONString(resultResponse.getData()), Qualification.class);
        return ResultResponse.successResponse(getQualificationResponse(qualification));
    }

    @Override
    public ResultResponse deleteQualification(Integer id) {
        return iQualificationService.deleteQualification(id);
    }

    private QualificationResponse getQualificationResponse(Qualification qualification) {
        QualificationResponse qualificationResponse = new QualificationResponse();
        BeanUtils.copyProperties(qualification, qualificationResponse);
        qualificationResponse.setUpdateTime(TimeUtils.toTimeStamp(qualification.getUpdateTime()));
        qualificationResponse.setCreateTime(TimeUtils.toTimeStamp(qualification.getCreateTime()));
        return qualificationResponse;
    }

    private QualificationVo getUser(RedisTemplate<String, Object> redisTemplate, String token, QualificationVo qualificationVo) {
        Object o = redisTemplate.opsForHash().get(token, "userMsg");
        AssertsUtil.isTrue(o == null, "请登录");
        Credentials credentials = JSONObject.parseObject(JSONObject.toJSONString(o), Credentials.class);
        qualificationVo.setUpdateBy(Integer.valueOf(String.valueOf(credentials.getId())));
        qualificationVo.setUpdateName(credentials.getName());
        return qualificationVo;
    }
}
