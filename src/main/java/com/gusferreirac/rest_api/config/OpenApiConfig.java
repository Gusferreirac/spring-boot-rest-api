package com.gusferreirac.rest_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rest Api with Spring Boot, Kubernetes and Docker")
                        .version("v1")
                        .description("A rest api using Java, Spring Boot, Kubernetes and Docker")
                        .license(new License()
                                .name("Apache 2.0")
                        )
                );
    }
}
