package com.elite.school.util;

/*
  The Class ExternalErrorResponse.
  This class is used for external device integration error message.
  @author sunil1
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ExternalErrorResponse {
    private Error error;

    @Getter
    @Setter
    public static class Error {
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        Integer code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        Map<String, String> details;
        String traceId ="ba846631-ea3b-4f68-b19b-774f701b91b0";
    }
}