package com.elite.school.util;

@Getter
@Setter
public class ExternalBaseResponse {
    public ExternalBaseResponse() {
        this.traceId = CorrelationIdFilter.getCorrelationId();
    }
    private String traceId;
}
