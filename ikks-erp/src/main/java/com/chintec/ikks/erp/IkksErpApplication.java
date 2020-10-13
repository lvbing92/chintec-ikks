package com.chintec.ikks.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/7 17:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class IkksErpApplication {
    public static void main(String[] args) {
        SpringApplication.run(IkksErpApplication.class, args);
    }
}
