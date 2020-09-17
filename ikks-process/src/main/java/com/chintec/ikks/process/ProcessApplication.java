package com.chintec.ikks.process;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/10 16:36
 */
@SpringBootApplication
@MapperScan("com.chintec.ikks.process.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class ProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class,args);
    }
}
