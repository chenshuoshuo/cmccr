package com.lqkj.web.cmccr2.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

/**
 * api文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Configure {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Lists.newArrayList(new BasicAuth("base")))
                .securityContexts(Lists.newArrayList(securityContext()))
                .directModelSubstitute(Timestamp.class, String.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lqkj.web.cmccr2"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("地图服务器")
                .description("地图服务器api")
                .termsOfServiceUrl("http://www.you07.com")
                .version("v2")
                .build();
    }

    /**
     * 授权信息
     */
    private SecurityContext securityContext() {
        SecurityReference osmSecurityReference = new SecurityReference("base",
                new AuthorizationScope[]{});

        return SecurityContext.builder()
                .forPaths(PathSelectors.any())
                .securityReferences(Lists.newArrayList(osmSecurityReference))
                .build();
    }
}
