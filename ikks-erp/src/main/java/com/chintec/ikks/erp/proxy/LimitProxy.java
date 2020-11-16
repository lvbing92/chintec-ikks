package com.chintec.ikks.erp.proxy;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.MenuFunction;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
        if (!s.contains("login") && !s.contains("logout") && !s.contains("userLogin")
                && !s.contains("user/roleAndMenu") && !s.contains("images")
                && !s.contains("videos") && !s.contains("files") && !s.contains("/userRegister")) {
            logger.info("环绕通知");
            String access_token = request.getHeader("access_token");
            logger.info(access_token);
            AssertsUtil.noLogin(StringUtils.isEmpty(access_token));
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String value = signature.getMethod().getDeclaredAnnotation(PermissionAnnotation.class).code();
            Object object = redisTemplate.opsForHash().get(access_token, "menuFunction");
            logger.info("redis: {}", object);
            if (object != null) {
                //获取用户信息
                logger.info("获取用户信息: {}", object);
                List<MenuFunction> menuFunctions = JSONObject.parseArray(JSONObject.toJSONString(object), MenuFunction.class);
                List<String> collect = menuFunctions.stream().map(MenuFunction::getFunctionCode).collect(Collectors.toList());
                AssertsUtil.noLogin(menuFunctions.size() == 0, "无权限访问!");
                if (!collect.contains(value)) {
                    AssertsUtil.noLogin(true);
                }
            } else {
                logger.info("没有权限");
                AssertsUtil.noLogin(true);
            }
        }
        return pjp.proceed();
    }
}
