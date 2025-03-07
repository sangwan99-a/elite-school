//package com.elite.school.framework.filter;
//
//
//import com.elite.school.util.ExternalErrorResponse;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//
//import java.util.UUID;
//
//@Component("accessFilter")
//public class AccessFilter extends GenericFilterBean {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);
////    @Autowired
////    EndpointAuthorizationService authorizationService;
////    @Autowired
////    private LoginService loginService;
////    @Value("${allowed.ip}")
////    private String allowedIp;
//
//    private AbtAuthenticator abtAuthenticator;
//
////    @Autowired
////    RegistrationService registrationService;
//
//    @PostConstruct
//    public void init() {
//        AbtAuthenticator anonymousAuth = new AnonymousAuthenticator();
////        AbtAuthenticator mtlsAuth = new MtlsAuthenticator();
//        AbtAuthenticator cognitoAuth = new CognitoAuthenticator(loginService, registrationService);
//
//        anonymousAuth.setNextAuthenticator(mtlsAuth);
//        mtlsAuth.setNextAuthenticator(cognitoAuth);
//
//        this.abtAuthenticator = anonymousAuth;
//    }
//
//    private static ExternalErrorResponse getExternalErrorResponse(String errorMessage) {
//        ExternalErrorResponse externalErrorResponse = new ExternalErrorResponse();
//        ExternalErrorResponse.Error error = new ExternalErrorResponse.Error();
//        error.setTraceId(UUID.randomUUID().toString());
//        error.setMessage(errorMessage);
//        externalErrorResponse.setError(error);
//        return externalErrorResponse;
//    }
//
//    @Override
//    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, jakarta.servlet.FilterChain chain) throws IOException, jakarta.servlet.ServletException {
//        final HttpServletRequest httpRequest = (HttpServletRequest) request;
//        final HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Set the Content Security Policy header issue fix for Zapscan Testing report
//        setContentSecurityPolicy(httpResponse);
//        setSecurityHeaders(httpResponse);
//
//        String requestURI = httpRequest.getRequestURI();
//        if (requestURI.contains("/.htaccess")) {
//            LOGGER.warn("Attempted access to .htaccess file: {}", requestURI);
//            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            unauthorized(httpResponse, "Access to .htaccess is forbidden");
//            return;
//        }
//
//        if (!StringUtils.isEmpty(httpRequest.getHeader(HttpHeaders.ORIGIN))) {
//            String origin = httpRequest.getHeader(HttpHeaders.ORIGIN);
//
//            if (allowedIp.contains(origin)) {
//                // Handle preflight (OPTIONS) request
//                if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
//                    httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
//                    httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS"); // Specify allowed methods
//                    httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, Origin"); // Specify allowed headers
//                    httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600"); // Cache the preflight request for 1 hour
//                    httpResponse.setStatus(HttpStatus.OK.value());
//                    return;
//                }
//
//                // For non-OPTIONS requests
//                httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
//                httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
//                httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, Origin");
//                httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization"); // Optionally expose certain headers
//            } else {
//                LOGGER.warn("Origin is not allowed: {}", origin);
//                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        boolean isAuthorized = abtAuthenticator.authenticate(httpRequest);
//        if (isAuthorized) {
//            chain.doFilter(request, response);
//
//        } else {
//            if (org.springframework.util.StringUtils.hasText(httpRequest.getHeader(AUTHORIZATION))) {
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                unauthorized(httpResponse, "User not authorised");
//            } else {
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                unauthorized(httpResponse, "Authorisation token is missing");
//            }
//        }
//    }
//
//    private String getUserIdFromRequest(HttpServletRequest httpRequest) throws IOException {
//        String userIdFromBody = authorizationService.extractUserIdFromBody(httpRequest);
//        String userIdFromQuery = authorizationService.extractUserIdFromQuery(httpRequest);
//        String userIdFromPath = authorizationService.extractUserIdFromPath(httpRequest);
//        String finalUserId = null;
//        // Prioritize the user ID extraction logic based on the availability
//        if (userIdFromBody != null && !userIdFromBody.isEmpty() && StringUtils.isNumeric(userIdFromBody)) {
//            finalUserId = userIdFromBody;
//            LOGGER.info("User ID extracted from request body: {}", finalUserId);
//        } else if (userIdFromQuery != null && !userIdFromQuery.isEmpty() && StringUtils.isNumeric(userIdFromQuery)) {
//            finalUserId = userIdFromQuery;
//            LOGGER.info("User ID extracted from query parameters: {}", finalUserId);
//        } else if (userIdFromPath != null && !userIdFromPath.isEmpty() && StringUtils.isNumeric(userIdFromPath)) {
//            finalUserId = userIdFromPath;
//            LOGGER.info("User ID extracted from path: {}", finalUserId);
//        }
//        return finalUserId;
//    }
//
//
//    private void unauthorized(HttpServletResponse response, String message) throws IOException {
//        ExternalErrorResponse externalErrorResponse = getExternalErrorResponse(message);
//        response.setContentType("application/json");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.getWriter().write(new ObjectMapper().writeValueAsString(externalErrorResponse));
//    }
//
//
//    private void setContentSecurityPolicy(HttpServletResponse response) {
//        response.setHeader("Content-Security-Policy", "default-src 'self'; img-src 'self' data:; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline';");
//    }
//
//    private void setSecurityHeaders(HttpServletResponse response) {
//        // Set various security headers
////        response.setHeader("X-Content-Type-Options", "nosniff");
////        response.setHeader("X-Frame-Options", "DENY"); // Prevents clickjacking
//        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains"); // Enforces HTTPS
////        response.setHeader("X-XSS-Protection", "1; mode=block"); // Enables XSS protection
//    }
//
//
//}