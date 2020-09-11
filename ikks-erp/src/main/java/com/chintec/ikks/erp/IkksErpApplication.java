package com.chintec.ikks.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/7 17:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.chintec.ikks.erp.mapper")
public class IkksErpApplication {
    public static void main(String[] args) {
        SpringApplication.run(IkksErpApplication.class,args);
    }
}
