package com.chintec.ikks.erp.proxy;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/27 14:40
 */
@Component
@Aspect
public class LimitProxy {
    private static final Logger logger = LogManager.getLogger(LimitProxy.class);
    @Autowired
    private HttpServletRequest request;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("execution(* com.chintec.ikks.erp.controller.*.*(..))")
    public void init() {
    }

    @Around("init()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String s = request.getRequestURL().toString();
        if (!s.contains("login") && !s.contains("logout") && !s.contains("userLogin")) {
            logger.info("环绕通知");
            String access_token = request.getHeader("access_token");
            logger.info(access_token);
            AssertsUtil.noLogin(StringUtils.isEmpty(access_token));
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String value = signature.getMethod().getDeclaredAnnotation(PermissionAnnotation.class).code();
            Object object = redisTemplate.opsForHash().get(access_token, "userMsg");
            if (object != null) {
                //获取用户信息
                CredentialsResponse credentialsResponse = JSONObject.parseObject(JSONObject.toJSON(object).toString(), CredentialsResponse.class);
                List<AuthorityMenuResponse> menuList = credentialsResponse.getMenuList();
                AssertsUtil.noLogin(menuList.size() == 0, "无权限访问!");
                AssertsUtil.noLogin(!menuList.contains(value), "无访问权限!");
            } else {
                AssertsUtil.noLogin(true);
            }
        }
        return pjp.proceed();
    }
}
