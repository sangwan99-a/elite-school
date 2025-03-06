//package com.elite.school.framework.filter;
//
//
//
///**
// * Utility class to obtaining OAuth token from Cognito for HTTP requests and invoking RESTful APIs.
// * Author: Sunil
// * Date: 30/04/2024
// */
//
//@Service
//public class ClientCredentialHelper {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCredentialHelper.class);
//
//    @Autowired
//    private AbtRestClient abtRestClient;
//    @Autowired
//    AbtApiConfig abtApiConfig;
//    @Autowired
//    private ObjectMapper objectMapper;
//    private Map<String, String> authorizationHeader;
//
//    private static final long REFRESH_TOKEN_INTERVAL_SECONDS = 3500;
//    // Define a variable to hold the last token retrieval time
//    private long lastTokenRetrievalTime = 0;
//
//
//    // Method to check if token needs to be reloaded
//    private boolean needsTokenReload() {
//        long currentTime = System.currentTimeMillis();
//        long elapsedTime = currentTime - lastTokenRetrievalTime;
//        return elapsedTime >= TimeUnit.SECONDS.toMillis(REFRESH_TOKEN_INTERVAL_SECONDS);
//    }
//
//    public synchronized Map<String, String> fetchABTAuthTokenHeader() {
//        if (needsTokenReload()) {
//            refreshAccessToken();
//            LOGGER.info("OAuth token reloaded successfully at: {}", new Date());
//        } else {
//            LOGGER.info("Using cached OAuth token obtained at: {}", new Date(lastTokenRetrievalTime));
//        }
//        return authorizationHeader;
//    }
//
//
//    private void refreshAccessToken() {
//        try {
//            CognitoTokenResponse cognitoTokenResponse = getAuthToken();
//            if (cognitoTokenResponse == null || cognitoTokenResponse.getAccess_token() == null) {
//                throw new RuntimeException("Error while retrieving ABT authentication token");
//            }
//            authorizationHeader = new HashMap<>();
//            authorizationHeader.put(CommonConstant.AUTHORIZATION_KEY, cognitoTokenResponse.getAccess_token());
//            lastTokenRetrievalTime = System.currentTimeMillis();
//        } catch (Exception e) {
//            LOGGER.error("Error while refreshing ABT authentication token {}", e.getMessage());
//            lastTokenRetrievalTime = 0L;
//            throw new RuntimeException("Error while retrieving ABT authentication token for internal services calls", e);
//        }
//    }
//
//
//    public CognitoTokenResponse getAuthToken() throws AbtExternalServiceException {
//        String url = abtApiConfig.getAbtInternalBaseUrl();
//        try {
//
//            // Prepare data for the request as MultiValueMap
//            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
//            data.add("grant_type", abtApiConfig.getAbtInternalGrantType());
//            data.add("client_id", abtApiConfig.getAbtInternalClientId()); // Ensure this matches
//            data.add("client_secret", abtApiConfig.getAbtInternalClientSecret()); // Ensure this matches
//            data.add("scope", abtApiConfig.getAbtInternalScope()); // Ensure this matches
//
//            // Prepare headers
//            Map<String, String> headers = new HashMap<>();
//            headers.put("Content-Type", "application/x-www-form-urlencoded");
//
//            // Invoke the API
//            ResponseEntity<String> response = abtRestClient.getAccessTokenByClientCredential(url, data, headers, String.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                return objectMapper.readValue(response.getBody(), CognitoTokenResponse.class);
//            } else {
//                LOGGER.warn("Failed to retrieve token, received HTTP status: {}", response.getStatusCode());
//
//            }
//
//        } catch (Exception e) {
//            LOGGER.error("Failed to retrieve ABT authentication token: {}", e.getMessage());
//        }
//        return null;
//    }
//
//
//}
//
