package com.chintec.ikks.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author tangxinli
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class,args);
    }
}

