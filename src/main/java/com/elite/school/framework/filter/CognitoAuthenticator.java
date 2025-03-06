package com.elite.school.framework.filter;

@RequiredArgsConstructor
public class CognitoAuthenticator extends AbtAuthenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CognitoAuthenticator.class);
    private final TokenUtils tokenUtils = new TokenUtils();
    private final LoginService loginService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final RegistrationService registrationService;


    private static final String[] URL_SKIP_CHECK_DB = {"/abt/wpbs/c-portal/get-custom-attribute", "/abt/wpbs/c-portal/da-userinfo"};

    @Override
    protected boolean canAuthenticate(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(authorizationHeader)) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Authentication attempt failed: No Authorization header present for request to {}", httpRequest.getServletPath());
            }
            return false;
        }

        try {
            String bearerToken = tokenUtils.extractToken(authorizationHeader);
            CognitoSessionInfo sessionInfo = loginService.isValidSSOToken(bearerToken);
            List<String> scopes = sessionInfo.getScopes();
            boolean isAuthorized = sessionInfo.isValid() && hasAccess(scopes, httpRequest.getServletPath());

            if (isAuthorized) {
                setUserAttributesFromToken(httpRequest, scopes, sessionInfo, bearerToken);
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Authentication successful for request to {}: Valid token and access granted.", httpRequest.getServletPath());
                }
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Authentication failed for request to {}: Token is invalid or access denied.", httpRequest.getServletPath());
                }
            }
            return isAuthorized;
        } catch (AbtExternalServiceException e) {
            LOGGER.error("Error during authentication for request to {}: {}", httpRequest.getServletPath(), e.getMessage());
            return false;
        }
    }

    private void setUserAttributesFromToken(HttpServletRequest httpRequest, List<String> scopes, CognitoSessionInfo sessionInfo, String bearerToken) throws AbtExternalServiceException {
        if (CollectionUtils.isEmpty(scopes) || !sessionInfo.getScopes().contains(EnumConstant.TokenScope.ABT_READWRITE.getValue())) {
            // Refactoring the username from bearer token
            Map<String, String> cognitoUserDetail = registrationService.getUserDetailsByToken(bearerToken);
            if (cognitoUserDetail != null && !cognitoUserDetail.isEmpty() && cognitoUserDetail.get(CommonConstant.AUTHTOKEN_USERNAME) != null) {
                String userName = cognitoUserDetail.get(CommonConstant.AUTHTOKEN_USERNAME);

                // If user is a DA (contains "/"), refactor to only use the actual username
                if (userName.contains("/")) {
                    userName = userName.substring(userName.lastIndexOf('/') + 1);
                }
                Long userId = loginService.getUserIdByUserName(userName);

                // Putting userId in Servlet attribute for further checking: customer portal security incident
                httpRequest.setAttribute(REQUEST_ATTRIBUTE_USER_ID, userId);
                httpRequest.setAttribute(REQUEST_ATTRIBUTE_OP_USER, Boolean.FALSE);
            }
        } else {
            httpRequest.setAttribute(REQUEST_ATTRIBUTE_OP_USER, Boolean.TRUE);
        }
    }




    private boolean hasAccess(List<String> scopes, String requestedUrl) {
        if (CollectionUtils.isEmpty(scopes)) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Access granted: No scopes provided for requested URL: {}", requestedUrl);
            }
            return true; // Full access if no scopes
        }

        if (scopes.contains(EnumConstant.TokenScope.ABT_READWRITE.getValue())) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Access granted: ABT Token has full access to API for requested URL: {}", requestedUrl);
            }
            return true;
        }

//        for (String scope : scopes) {
//            if (SCOPE_URL_MAP.containsKey(scope)) {
//                String[] allowedUrls = SCOPE_URL_MAP.get(scope);
//                if (allowedUrls != null && matchesAny(allowedUrls, requestedUrl)) {
//                    LOGGER.info("Access granted for scope '{}' to requested URL: {}", scope, requestedUrl);
//                    return true;
//                }
//            }
//        }
//
        LOGGER.warn("Access denied: No valid scope found for requested URL: {}", requestedUrl);
        return false;
    }


    private boolean matchesAny(String[] allowedUrls, String requestedUrl) {
        return Arrays.stream(allowedUrls).anyMatch(url -> pathMatcher.match(url, requestedUrl));
    }
}
