package com.elite.school.framework.filter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AbtApiConfig.class)
public class UrlMapperConfiguration {

    @Bean
    public AbtApiUrlMapper abtApiUrlMapper(AbtApiConfig abtApiConfig) {
        return new AbtApiUrlMapper(abtApiConfig);
    }
}
