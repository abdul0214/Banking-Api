package com.moneytransfer.api.config;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String REGEX_API = "/api.+";

    private static ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfo(title, description, version, null, null, null, null, new ArrayList<>());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Bank API")
                .apiInfo(apiInfo("Banking API", "API documentation for backend module of Banking Project", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(REGEX_API))
                .build();
    }
}