package com.wmh.wechat.api.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Profile({ "dev", "test" }) 指定环境
@Configuration
@EnableSwagger2
//在1.9.0版本或之后版本，SwaggerBootstrapUi提供了简单的Basic认证功能
//@EnableSwaggerBootstrapUI 账户权限配置
public class SwaggerConfig {

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // api扫包
                .apis(RequestHandlerSelectors.basePackage("com.wmh.wechat.service"))
                // 接口地址前缀
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微信服务系统")
                .description("微信服务接口")
                .termsOfServiceUrl("http://127.0.0.1:9090")
                .contact(new Contact("karma", null, null))
                .version("1.0")
                .build();
    }

}