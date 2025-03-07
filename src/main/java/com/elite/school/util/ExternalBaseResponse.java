package com.elite.school.util;

import com.elite.school.framework.filter.CorrelationIdFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalBaseResponse {
    public ExternalBaseResponse() {
        this.traceId = CorrelationIdFilter.getCorrelationId();
    }
    private String traceId;
}
