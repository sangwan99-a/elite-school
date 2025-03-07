package com.elite.school.framework.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

public class AnonymousAuthenticator extends AbtAuthenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousAuthenticator.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean canAuthenticate(HttpServletRequest httpRequest) {
        boolean isAllowedAccess = false;
        try {
            String requestedUrl = httpRequest.getServletPath();
            //Implement using enums or list
            String[] allowedUrl = {
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/actuator/info",
                    "/actuator/health",
                    "/v3/api-docs/swagger-config"};
            if (Arrays.stream(allowedUrl).anyMatch(s -> pathMatcher.match(s, requestedUrl))) {
                isAllowedAccess = true;
            }
            return isAllowedAccess;
        } catch (Exception e) {
            LOGGER.error("Failed to authenticate request in URL: {}, ErrorMessage:{}", httpRequest.getServletPath(), e.getMessage());
            return isAllowedAccess;
        }
    }
}