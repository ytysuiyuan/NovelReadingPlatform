package com.example.novelreading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 配置了Swagger的Docket的bean实例
     *
     * @return
     */
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Name")
                // enable是否启动Swagger，如果为false，则swagger不能在浏览器中访问
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.novelreading.controller"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    public ApiInfo apiInfo() {

        // 作者信息
        Contact contact = new Contact("yty", "", "");

        return new ApiInfo("SpringBoot-VUE-Novel API Documentation",
                "Api Documentation",
                "v1.0", "urn:tos",
                contact, "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }


    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
