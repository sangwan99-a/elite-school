package com.elite.school.util;

/**
 * Utility class for handling HTTP requests and invoking RESTful APIs.
 * Author: Sunil
 * Date: 14/02/2024
 */
@Service
public class AbtRestClientImpl implements AbtRestClient {
    private static final Logger LOG = LoggerFactory.getLogger(AbtRestClientImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    /**
     * Invokes a POST request to a specified URL.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param data         The data to be sent in the request body.
     * @param headerMap    Additional headers to be included in the request.
     * @param responseType The expected response type.
     * @return The response body.
     * @throws AbtExternalServiceException If an error occurs during the request.
     */
    @Override
    public <T> T invokePostURL(final EnumConstant.ModuleUri module, final String url, final Object data, final Map<String, String> headerMap,
                               final Class<T> responseType) throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            final HttpHeaders headers = prepareHeaders(headerMap);
            urlMapper.injectAuth(module, headers);
            LastURLHolder.setLastURL(urlMapper.getServiceBaseUri(module) + url);
            String jsonData = prepareJsonObject(data);
            final HttpEntity<?> request = new HttpEntity<>(jsonData, headers);
            LOG.debug("Invoking POST request uri: {}", LastURLHolder.getLastURL());
            ResponseEntity<T> responseEntity = invokeRestTemplateURL(LastURLHolder.getLastURL(), HttpMethod.POST, request, responseType);
            LOG.debug("Response from POST request: {}", responseEntity.getBody());
            return responseEntity.getBody();
        } finally {
            getTotalTimeTaken(stopWatch);
        }
    }


    /**
     * Invokes a GET request to a specified URL.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param data         The data to be sent in the request body.
     * @param responseType The expected response type.
     * @return The response body.
     * @throws AbtExternalServiceException If an error occurs during the request.
     */
    @Override
    public <T> T invokeGetURL(final EnumConstant.ModuleUri module, final String url, final Object data, final Class<T> responseType)
            throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            LastURLHolder.setLastURL(urlMapper.getServiceBaseUri(module) + url);
            String jsonData = prepareJsonObject(data);
            final HttpEntity<?> request = new HttpEntity<>(jsonData);
            ResponseEntity<T> responseEntity = invokeRestTemplateURL(LastURLHolder.getLastURL(), HttpMethod.GET, request, responseType);
            return responseEntity.getBody();
        } finally {
            getTotalTimeTaken(stopWatch);
        }
    }

    /**
     * Invokes a POST request with a file upload.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param file         The file to be uploaded.
     * @param data         The data to be sent in the request body.
     * @param responseType The expected response type.
     * @return The response body.
     * @throws AbtExternalServiceException If an error occurs during the request.
     * @throws IllegalStateException       If the file cannot be processed.
     */
    @Override
    public <T> T invokeFilePostURL(final EnumConstant.ModuleUri module, final String url, final MultipartFile file, final Object data, final Map<String, String> headerMap,
                                   final Class<T> responseType) throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set(CorrelationIdFilter.CORRELATION_ID_HEADER, CorrelationIdFilter.getCorrelationId());
            urlMapper.injectAuth(module, headers);
            LastURLHolder.setLastURL(urlMapper.getServiceBaseUri(module) + url);
            MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();
            if (file != null && !file.isEmpty()) {
                multipartBody.add("file", file.getResource());
            }
            if (data != null) {
                multipartBody.add("data", data);
            }

            final HttpEntity<?> request = new HttpEntity<>(multipartBody, headers);
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new FormHttpMessageConverter());
            template.getMessageConverters().add(new ByteArrayHttpMessageConverter());

            ResponseEntity<T> responseEntity = template.exchange(LastURLHolder.getLastURL(),
                    HttpMethod.POST, request, responseType);

            return responseEntity.getBody();
        } finally {
            getTotalTimeTaken(stopWatch);

        }
    }


    /**
     * Invokes a GET request with specified parameters.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param dataMap      The parameters to be included in the request.
     * @param responseType The expected response type.
     * @param headerMap    Additional headers to be included in the request.
     * @return The response body.
     * @throws AbtExternalServiceException If an error occurs during the request.
     */

    @Override
    public <T> T invokeGetURL(final EnumConstant.ModuleUri module, final String url, final Map<String, String> dataMap, final Map<String, String> headerMap, final Class<T> responseType)
            throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            final HttpHeaders headers = prepareHeaders(headerMap);
            urlMapper.injectAuth(module, headers);
            LastURLHolder.setLastURL(urlMapper.getServiceBaseUri(module) + url);
            urlMapper.injectAuth(module, headers);
            URI finalUrl = getUriBuilder(urlMapper.getServiceBaseUri(module) + url, dataMap);
            final HttpEntity<?> request = new HttpEntity<>(headers);
            ResponseEntity<T> responseEntity = invokeRestTemplateURL(finalUrl.toString(), HttpMethod.GET, request, responseType);
            return responseEntity.getBody();
        } finally {
            getTotalTimeTaken(stopWatch);
        }
    }

    private  void getTotalTimeTaken(StopWatch stopWatch) {
        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        String totalTime= String.valueOf(totalTimeSeconds);

        String logMessage = String.format("Time taken by request to %s : %s seconds", LastURLHolder.getLastURL(), totalTime);
        SubRequestTimesHolder.addSubRequestLog(logMessage);
        LOG.info("Time taken by request to {} : {} seconds", LastURLHolder.getLastURL(), stopWatch.getTotalTimeSeconds());
    }


    /**
     * Invokes an external POST request to a specified URL from the ABT system.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param data         The data to be sent in the request body.
     * @param headerMap    Additional headers to be included in the request.
     * @param responseType The expected response type.
     * @return The response entity.
     * @throws AbtExternalServiceException If an error occurs during the request.
     */
    @Override
    public <T> ResponseEntity<T> invokeExternalPostUrl(final EnumConstant.ModuleUri module, final String url, final Object data, final Map<String, String> headerMap,
                                                       final Class<T> responseType) throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            final HttpHeaders headers = prepareHeaders(headerMap);
            urlMapper.injectAuth(module, headers);
            LastURLHolder.setLastURL(urlMapper.getServiceBaseUri(module) + url);
            String jsonData = prepareJsonObject(data);
            final HttpEntity<?> request = new HttpEntity<>(jsonData, headers);
            ResponseEntity<T> responseEntity = invokeRestTemplateURL(LastURLHolder.getLastURL(), HttpMethod.POST, request, responseType);
            LOG.debug("Response from external POST request: {}", responseEntity);
            return responseEntity;
        } finally {
            getTotalTimeTaken(stopWatch);
        }
    }

    /**
     * Invokes an external GET request to a specified URL from the ABT system.
     *
     * @param module       The module associated with the URL.
     * @param url          The specific URL endpoint.
     * @param params       The parameters to be included in the request.
     * @param headerMap    Additional headers to be included in the request.
     * @param responseType The expected response type.
     * @return The response entity.
     * @throws AbtExternalServiceException If an error occurs during the request.
     */
    @Override
    public <T> ResponseEntity<T> invokeExternalGetUrl(final EnumConstant.ModuleUri module, final String url, final Map<String, String> params,
                                                      final Map<String, String> headerMap, final Class<T> responseType) throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            AbtApiUrlMapper urlMapper = getAbtApiUrlMapper();
            URI finalUrl = getUriBuilder(urlMapper.getServiceBaseUri(module) + url, params);
            final HttpHeaders headers = prepareHeaders(headerMap);
            urlMapper.injectAuth(module, headers);
            LastURLHolder.setLastURL(finalUrl.toString());
            // Create the HTTP request using the header information only (no request body for GET)
            final HttpEntity<?> request = new HttpEntity<>(headers);
            ResponseEntity<T> responseEntity = invokeRestTemplateURL(LastURLHolder.getLastURL(), HttpMethod.GET, request, responseType);
            LOG.debug("Response from external GET request: {}", responseEntity);
            return responseEntity;
        } finally {
            getTotalTimeTaken(stopWatch);
        }

    }

    /**
     * Constructs a URI with query parameters.
     *
     * @param url    The base URL.
     * @param params The query parameters.
     * @return The constructed URI.
     */
    private URI getUriBuilder(String url, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // Add request parameters if they exist
        if (params != null && !params.isEmpty()) {
            params.forEach(builder::queryParam);
        }
        return builder.build().encode().toUri();
    }

    /**
     * Retrieves the AbtApiUrlMapper bean from the application context.
     *
     * @return The AbtApiUrlMapper bean.
     */
    private AbtApiUrlMapper getAbtApiUrlMapper() {
        AbtApiUrlMapper urlMapper = StaticContextAccessor.getBean(AbtApiUrlMapper.class);
        Assert.notNull(urlMapper, "urlMapper is null, please configure it in Spring Component scanner");
        return urlMapper;
    }

    /**
     * Invokes a RESTful API using RestTemplate.
     *
     * @param url          The URL of the API.
     * @param httpMethod   The HTTP method (GET, POST, etc.).
     * @param request      The HTTP request entity.
     * @param responseType The expected response type.
     * @param <T>          The type of the response.
     * @return The ResponseEntity containing the response body and status.
     */
    private <T> ResponseEntity<T> invokeRestTemplateURL(String url, HttpMethod httpMethod, HttpEntity<?> request, Class<T> responseType) throws AbtExternalServiceException {
        try {
            return restTemplate.exchange(url, httpMethod, request, responseType);
        } catch (HttpClientErrorException e) {
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            String responseBodyAsString = e.getResponseBodyAsString();
            LOG.error("HTTP Client Error: {}", responseBodyAsString);
            ExternalErrorResponse errorResponse = null;
            try {
                // Configure ObjectMapper to ignore unknown properties
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                errorResponse = objectMapper.readValue(responseBodyAsString, ExternalErrorResponse.class);
            } catch (IOException | RuntimeException exception) {
                LOG.error("Failed to deserialize error response: {}", exception.getMessage());
            }
            if (errorResponse != null && errorResponse.getError() != null) {
                throw new AbtExternalServiceException(errorResponse.getError().getMessage(), errorResponse.getError().getDetails(), statusCode);
            } else {
                throw new AbtExternalServiceException("HTTP Client Error: " + e.getMessage(), e);
            }
        } catch (RestClientException e) {
            // Handle RestClientException separately if needed
            LOG.error("Rest Client Error: {}", e.getMessage());
            throw new AbtExternalServiceException("Rest Client Error: " + e.getMessage(), e);
        } catch (Exception e) {
            // Wrap any other exceptions in AbtExternalServiceException
            LOG.error("Unexpected Error: {}", e.getMessage());
            throw new AbtExternalServiceException("Unexpected Error: " + e.getMessage(), e);
        }
    }


    /**
     * Prepares JSON data from the given object.
     *
     * @param data The data object to be converted to JSON.
     * @return The JSON representation of the data.
     * @throws AbtExternalServiceException If an error occurs during JSON serialization.
     */

    private String prepareJsonObject(Object data) throws AbtExternalServiceException {
        String jsonData = null;
        // Convert the data of POST request into JSON format
        if (data != null && !(data instanceof String)) {
            final ObjectMapper objMapper = new ObjectMapper();
            try {
                objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                jsonData = objMapper.writeValueAsString(data);
            } catch (Exception ex) {
                throw new AbtExternalServiceException(ex.getMessage(),ex);
            }
        } else if (data != null) {
            jsonData = data.toString();
        }
        return jsonData;
    }

    /**
     * Prepares HttpHeaders with default values and additional headers.
     *
     * @param headerMap Additional headers to be included.
     * @return The prepared HttpHeaders object.
     */
    private HttpHeaders prepareHeaders(Map<String, String> headerMap) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(CorrelationIdFilter.CORRELATION_ID_HEADER, CorrelationIdFilter.getCorrelationId());
        // Iterate over the headers map and create the POST request header
        Optional.ofNullable(headerMap)
                .filter(map -> !map.isEmpty())
                .ifPresent(map -> map.forEach(headers::add));
        return headers;
    }


    @Override
    public <T> ResponseEntity<T> getAccessTokenByClientCredential(final String url, final Object data, final Map<String, String> headerMap, final Class<T> responseType) throws AbtExternalServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            HttpHeaders headers = createHeaders(headerMap);
            HttpEntity<Object> request = new HttpEntity<>(data, headers);

            ResponseEntity<T> responseEntity = invokeRestTemplateURL(url, HttpMethod.POST, request, responseType);
            LOG.debug("Response from external POST request: {}", responseEntity);

            return responseEntity;
        } finally {
            getTotalTimeTaken(stopWatch);
        }
    }

    private HttpHeaders createHeaders(Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();

        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach(headers::add);
        }

        return headers;
    }




}
