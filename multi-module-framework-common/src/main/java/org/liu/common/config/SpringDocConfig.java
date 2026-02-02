package org.liu.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    /**
     * 全局API文档配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // API文档基本信息
                .info(new Info()
                        .title("liu项目API文档") // 文档标题
                        .version("1.0.0") // 版本
                        .description("基于Spring Boot 3.2.8 + SpringDoc OpenAPI的API文档") // 描述
                        // 联系人信息（可选）
                        .contact(new Contact()
                                .name("开发团队")
                                .email("dev@example.com")
                                .url("https://example.com"))
                        // 许可证（可选）
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }

}
