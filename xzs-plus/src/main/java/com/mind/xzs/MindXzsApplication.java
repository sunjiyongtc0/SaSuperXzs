package com.mind.xzs;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@MapperScan("com.mind.*.mapper")
@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MindXzsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindXzsApplication.class,args);
    }
}
