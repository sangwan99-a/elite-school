package com.elite.school.framework.filter;

import org.slf4j.MDC;

import java.util.UUID;

public class CorrelationIdFilter extends GenericFilterBean {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String X_AMAZON_ID_HEADER = "x-amzn-RequestId";

    public static final String CORRELATION_ID_MDC = "correlationId";
//    public static final String AUTHORIZATION = "authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

//        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        String correlationId = request.getHeader(X_AMAZON_ID_HEADER);

//        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = generateCorrelationId();
        }
        MDC.put(CORRELATION_ID_MDC, correlationId);
//        MDC.put(AUTHORIZATION, authorizationHeader);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID_MDC);
        }
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID_MDC); // Access the correlation ID from the ThreadLocal
    }
//
//    public static String getAuthorization() {
//        return MDC.get(AUTHORIZATION); // Access the Bearer token  from the ThreadLocal
//    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
