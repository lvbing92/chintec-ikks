package com.chintec.ikks.erp.service.utils;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.AssertsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/3 11:02
 */
@Slf4j
public class UserUtils {

    public static CredentialsResponse getCredentialsResponse(RedisTemplate<String, Object> redisTemplate, String token) {
        Object o = redisTemplate.opsForHash().get(token, "userMsg");
        log.info("用户信息:{}", o);
        AssertsUtil.noLogin(o == null, "请登录");
        return JSONObject.parseObject(JSONObject.toJSONString(o), CredentialsResponse.class);
    }

    public static Object getUser(RedisTemplate<String, Object> redisTemplate, String token, QualificationVo qualificationVo, QualificationSupplierVo qualificationSupplierVo) {
        Object o = redisTemplate.opsForHash().get(token, "userMsg");
        AssertsUtil.noLogin(o == null, "请登录");
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
