//package com.elite.school.util;
//
//import org.springframework.http.ResponseEntity;
//
//import java.util.Map;
//
//public interface AbtRestClient {
//
//    <T> T invokePostURL(final EnumConstant.ModuleUri module, final String url, final Object data, final Map<String, String> headerMap,
//                        final Class<T> responseType) throws AbtExternalServiceException;
//    <T> T invokeFilePostURL(final EnumConstant.ModuleUri module, final String url, final MultipartFile file, final Object data, final Map<String, String> headerMap,
//                            final Class<T> responseType) throws AbtExternalServiceException;
//
//    <T> T invokeGetURL(final EnumConstant.ModuleUri module, final String url, final Object data, final Class<T> responseType)
//            throws AbtExternalServiceException;
//
//    <T> T invokeGetURL(final EnumConstant.ModuleUri module, final String url, final Map<String, String> dataMap, final Map<String, String> headerMap, final Class<T> responseType)
//            throws AbtExternalServiceException;
//
//    <T> ResponseEntity<T> invokeExternalPostUrl(final EnumConstant.ModuleUri module, final String url, final Object data, final Map<String, String> headerMap,
//                                                final Class<T> responseType) throws AbtExternalServiceException;
//
//    <T> ResponseEntity<T> invokeExternalGetUrl(final EnumConstant.ModuleUri module, final String url, final Map<String, String> params,
//                                               final Map<String, String> headerMap, final Class<T> responseType) throws AbtExternalServiceException;
//    <T> ResponseEntity<T> getAccessTokenByClientCredential(final String url, final Object data, final Map<String, String> headerMap,
//                                                           final Class<T> responseType) throws AbtExternalServiceException;
//}
