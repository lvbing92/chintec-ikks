package com.chintec.ikks.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
            String access_token = request.getHeader("Authorization");
            log.info("token:{}", access_token);
            template.header("Authorization",  access_token);
        }
    }
}
