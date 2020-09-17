package com.chintec.ikks.process;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/10 16:36
 */
@SpringBootApplication
@MapperScan("com.chintec.ikks.process.mapper")
public class ProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class,args);
    }
}
