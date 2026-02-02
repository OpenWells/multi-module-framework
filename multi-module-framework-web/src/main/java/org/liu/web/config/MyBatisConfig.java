package org.liu.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 独立的MyBatis配置类（多模块推荐）
 * 专门负责Mapper扫描，与启动类解耦
 */
@Configuration
@MapperScan(
        basePackages = "org.liu", // 精确扫描biz模块的Mapper
        annotationClass = org.apache.ibatis.annotations.Mapper.class // 仅扫描加了@Mapper的接口（可选）
)
public class MyBatisConfig {
    // 可扩展：自定义SqlSessionFactory、分页插件等（多模块常用）
}