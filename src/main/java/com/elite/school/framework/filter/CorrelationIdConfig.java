package com.elite.school.framework.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Filter;

@Configuration
public class CorrelationIdConfig implements WebMvcConfigurer {

    @Bean
    public Filter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add any other interceptors if needed
    }
}
