package org.liu.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot3启动类（JDK17适配）
 * @ComponentScan：扫描所有模块的Bean
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.liu")
@MapperScan(basePackages = "org.liu.core.mapper") // 仅扫描core层的Mapper接口
@EnableDiscoveryClient // 开启Nacos服务注册发现（核心注解）
@EnableTransactionManagement // 开启事务管理（biz层事务生效）
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        System.out.println("======= 多模块框架启动成功（JDK17+Spring Boot3）=======");
    }
}