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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/7/22 23:41
 */
@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String s = request.getRequestURL().toString();
        log.info(s);
        if (!s.contains("login") && !s.contains("logout") && !s.contains("userLogin")) {
            String access_token = request.getHeader("access_token");

            log.info("token:{}", access_token);
            template.header("Authorization", "Bearer " + access_token);
        }
    }
}
