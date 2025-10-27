package com.gusferreirac.rest_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//Config the possible response formats
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        ==================================
//           Config via Query Param
//        ==================================
//        configurer.favorParameter(true)
//                .parameterName("mediaType")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);

//        ==================================
//           Config via Header Param
//        ==================================
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
               .mediaType("x-yaml", MediaType.APPLICATION_YAML);
    }
}
