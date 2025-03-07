//package com.elite.school.util;
//
//
//import com.elite.school.framework.filter.CorrelationIdFilter;
//import com.elite.school.framework.filter.LastURLHolder;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@ControllerAdvice
//public class AbtExceptionHandler {
//
//    private static final Logger LOG = LoggerFactory.getLogger(AbtExceptionHandler.class);
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ExternalErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentTypeMismatchException exception) {
//        //LOG.error("Handling error for last URL hit = {} ,exception message ={}, trace = {}", LastURLHolder.getLastURL(), exception.getMessage(), exception);
//        LOG.error("MethodArgumentTypeMismatchException Error Details:\n" +
//                        "  Last URL hit: {}\n" +
//                        "  Correlation ID: {}\n" +
//                        "  Exception message: {}",
//                LastURLHolder.getLastURL(),
//                CorrelationIdFilter.getCorrelationId(),
//                exception.getMessage());
//        LOG.error("Stack trace", exception);
//
//        ExternalErrorResponse.Error error = new ExternalErrorResponse.Error();
//        error.setMessage(exception.getMessage());
//        error.setTraceId(CorrelationIdFilter.getCorrelationId());
//        ExternalErrorResponse externalErrorResponse = new ExternalErrorResponse();
//        externalErrorResponse.setError(error);
//        return new ResponseEntity<>(externalErrorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(AbtExternalServiceException.class)
//    public ResponseEntity<ExternalErrorResponse> handleAbtExternalServiceException(HttpServletRequest request, HttpServletResponse response, AbtExternalServiceException exception) {
////        LOG.error("Handling error for last URL hit = {} , exception message = {}, trace = {}", LastURLHolder.getLastURL(), exception.getMessage(), exception);
////        LOG.error("Correlation ID: {} - Exception root cause Details: {}",CorrelationIdFilter.getCorrelationId(), exception.getRootCauseDetails());
//
//        LOG.error("Error Details:\n" +
//                        "  Last URL hit: {}\n" +
//                        "  Correlation ID: {}\n" +
//                        "  Exception message: {}\n" +
//                        "  Root cause details: {}\n",
//                LastURLHolder.getLastURL(),
//                CorrelationIdFilter.getCorrelationId(),
//                exception.getMessage(),
//                exception.getRootCauseDetails());
//        LOG.error("Stack trace", exception);
//
//        ExternalErrorResponse.Error error = new ExternalErrorResponse.Error();
//        error.setMessage(exception.getMessage());
//        error.setCode(exception.getCode());
//        error.setDetails(exception.getDetails());
//        error.setTraceId(CorrelationIdFilter.getCorrelationId());
//        ExternalErrorResponse externalErrorResponse = new ExternalErrorResponse();
//        externalErrorResponse.setError(error);
//        return new ResponseEntity<>(externalErrorResponse, exception.getStatusCode());
//    }
//
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ExternalErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
////        LOG.error("Handling error for last URL hit = {} , exception message = {}, trace = {}", LastURLHolder.getLastURL(), ex.getMessage(), ex);
//
//        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
//                .collect(Collectors.toMap(errorM -> ((FieldError) errorM).getField(), errorM -> errorM.getDefaultMessage()));
//
//
//        LOG.error("Validation failed for request at URL: {}\n" +
//                        "Exception message: {}\n" +
//                        "Correlation ID: {}\n" +
//                        "Invalid parameters: {}\n",
//                LastURLHolder.getLastURL(),
//                ex.getMessage(),
//                CorrelationIdFilter.getCorrelationId(),
//                errors);
//        LOG.error("Stack trace", ex);
//
//        ExternalErrorResponse.Error error = new ExternalErrorResponse.Error();
//        error.setMessage("Invalid request parameters");
//        error.setTraceId(CorrelationIdFilter.getCorrelationId());
//        error.setDetails(errors);
//        ExternalErrorResponse externalErrorResponse = new ExternalErrorResponse();
//        externalErrorResponse.setError(error);
//        return ResponseEntity.badRequest().body(externalErrorResponse);
//    }
//
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ExternalErrorResponse> handleRuntimeException(HttpServletRequest request, HttpServletResponse response, RuntimeException runtimeException) {
//        ExternalErrorResponse.Error error = new ExternalErrorResponse.Error();
//        if (runtimeException != null && runtimeException.getStackTrace().length > 0) {
//            error.setMessage(runtimeException.getMessage());
////            LOG.error("Handling error for last URL hit = {}, exception message = {}, trace = {} ", LastURLHolder.getLastURL(), runtimeException.getMessage(), runtimeException);
//        }
//
//        LOG.error("Runtime Exception Error Details:\n" +
//                        "  Last URL hit: {}\n" +
//                        "  Correlation ID: {}\n" +
//                        "  Exception message: {}\n",
//                LastURLHolder.getLastURL(),
//                CorrelationIdFilter.getCorrelationId(),
//                runtimeException.getMessage());
//
//        LOG.error("Stack trace", runtimeException);
//        error.setTraceId(CorrelationIdFilter.getCorrelationId());
//        ExternalErrorResponse externalErrorResponse = new ExternalErrorResponse();
//        externalErrorResponse.setError(error);
//        return new ResponseEntity<>(externalErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}