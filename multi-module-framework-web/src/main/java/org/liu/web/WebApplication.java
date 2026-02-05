package org.liu.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot3启动类（JDK17适配）
 * @ComponentScan：扫描所有模块的Bean
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.liu")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        System.out.println("======= 多模块框架启动成功（JDK17+Spring Boot3）=======");
    }
}