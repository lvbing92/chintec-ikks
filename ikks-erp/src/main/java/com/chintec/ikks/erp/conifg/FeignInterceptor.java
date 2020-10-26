package com.chintec.ikks.erp.conifg;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.util.AssertsUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/7/22 23:41
 */
@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String s = request.getRequestURL().toString();
        log.info(s);
        if (!s.contains("login") && !s.contains("logout") && !s.contains("userLogin")) {
            String access_token = request.getHeader("access_token");
            Object object = redisTemplate.opsForHash().get(access_token, "userMsg");
            if (!ObjectUtils.isEmpty(object)) {
                //获取用户信息
                CredentialsResponse credentialsResponse = JSONObject.parseObject(JSONObject.toJSON(object).toString(), CredentialsResponse.class);
                List<AuthorityMenuResponse> menuList = credentialsResponse.getMenuList();
                AssertsUtil.isTrue(menuList.size() == 0, "无权限访问!");
                String[] v1s = s.split("v1");
                String url = v1s[1];
                AssertsUtil.isTrue(!menuList.contains(url),"无访问权限!");
            }
            log.info("token:{}", access_token);
            template.header("Authorization", "Bearer " + access_token);
        }
    }
}
