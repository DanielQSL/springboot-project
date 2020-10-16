package com.company.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 *
 * @author qianshuailong
 */
@Profile({"dev", "test"})
@EnableSwagger2
@Configuration
public class Swagger2Config {

    private static final String BASE_PACKAGE = "com.company.project.springbootproject.controller";

    /**
     * swagger2的配置文件
     *
     * @return swagger2配置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //注册整体api信息
                .apiInfo(apiInfo())
                .select()
                //指定扫描的包
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建api文档的详细信息函数
     *
     * @return api文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("XXX项目后端api接口文档")
                //描述
                .description("欢迎访问XXX项目后端接口文档，以下是各功能模块接口的详细信息")
                //联系人
                .contact(new Contact("DanielQSL", "", "DanielQSL@126.com"))
                //版本号
                .version("1.0.0")
                .build();
    }
}
