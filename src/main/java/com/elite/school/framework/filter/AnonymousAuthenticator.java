package com.elite.school.framework.filter;

import java.util.Arrays;

public class AnonymousAuthenticator extends AbtAuthenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousAuthenticator.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean canAuthenticate(HttpServletRequest httpRequest) {
        boolean isAllowedAccess = false;
        try {
            String requestedUrl = httpRequest.getServletPath();
            //Implement using enums or list
            String[] allowedUrl = {
//                    ApiConstants.WEB_PORTAL_SEARCH_ACCOUNT + ApiConstants.FETCH_USER_INFO,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CARD_DETAILS_BY_USER_ID,
                    ApiConstants.WEB_PORTAL_PURCHASE_PRODUCT_CONTROLLER+ApiConstants.CONCESSION_EXPIRY_NOTIFICATION_BEFORE_ONE_DAY,
//                    ApiConstants.WEB_PORTAL_PURCHASE_PRODUCT_CONTROLLER+ApiConstants.CONCESSION_EXPIRY_NOTIFICATION,
//                    ApiConstants.WEB_PORTAL_PURCHASE_PRODUCT_CONTROLLER+ApiConstants.CONCESSION_EXPIRED_NOTIFICATION,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_EMAIL_BY_USER_ID,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_USER_DETAIL_BY_USER_ID_PARAM,

                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ARCHIVE_DELINK_EMV,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.AUTO_TOP_UP_PAYMENT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ABT_WEB_BUSINESS_CALL_BACK_BUY_MEDIA_ANDPRODUCT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ABT_WEB_BUSINESS_CALL_BACK_QUICK_RECHARGE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ABT_WEB_BUSINESS_CALL_BACK_RE_ISSUE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GENERATE_PREPAID_SJT_QR_CODE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.OPERATOR_UNREG_CARD_TRANSACTION_REPORT,
                    ApiConstants.CARD_TRANSACTION_CONTROLLER + ApiConstants.SEARCH_DATA,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.OPERATOR_CARD_TRANSACTION_CONTROLLER,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.OPERATOR_UNREG_FARE_INVOICE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CHECK_MEDIA_EXISTS,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CHECK_USER_ID_EXISTS,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.DOWNLOAD_QR_PDF,
//                    ApiConstants.WEB_PORTAL_PURCHASE_PRODUCT_CONTROLLER + ApiConstants.PURCHASE_PRODUCT_ORDER_PAYMENT,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.FETCH_USER_INFO,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.FORGET_PASSWORD,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.FORGET_PASSWORD_CONFIRM,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ABT_WEB_BUSINESS_GENERATE_CHECKSUM,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GENERATE_SJT_QR_IMAGE,

//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.FETCH_ACCOUNT_NO_BY_USER_ID_PARAM,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_CONTACT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_DIGITAL_ID_TOKEN,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_EMAIL,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_SJT_CONCESSION_LIST,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_SJT_FARE_PRICE,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_USERNAME,
                    ApiConstants.CPORTAL_BASE_URL + CommonConstant.MAPPING_LOGIN,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CPORTAL_LOGOUT,
                    CommonConstant.MAPPING_CPORTAL_MASTER_CONTROLLER + ApiConstants.COMPANY_LIST,
                    CommonConstant.MAPPING_CPORTAL_MASTER_CONTROLLER + ApiConstants.LOAD_ROUTE_LIST,
                    CommonConstant.MAPPING_CPORTAL_MASTER_CONTROLLER + ApiConstants.LOAD_SJT_PASSENGER_TYPE_FARE_PRICE,
                    CommonConstant.MAPPING_CPORTAL_MASTER_CONTROLLER + ApiConstants.LOAD_SJT_FARE_PRODUCT_TYPE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_MAX_ACCOUNT_BALANCE_CONFIG,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.MIN_BAL_AUTO_TOP_UP_PAYMENT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_MINIMUM_RECHARGE_AMOUNT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.NOTIFY_CARD_EXPIRY,
                    ApiConstants.PARK_AND_RIDE_BASE_URL + ApiConstants.FETCH_ACCOUNT_BALANCE_BY_VEHICLENO,
                    ApiConstants.PASSENGER_MAINTENANCE_CONTROLLER_CPORTAL + ApiConstants.CPORTAL_SEARCH_CUSTOMER,
                    CommonConstant.WINDCAVE_PAYMENT_CONTROLLER + ApiConstants.SESSION_FOR_SJT_PURCHASE,
                    CommonConstant.WINDCAVE_PAYMENT_CONTROLLER + ApiConstants.NON_REG_SESSION_FOR_PURCHASE,

                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.PURCHASE_SJT_QR_TICKET_EMAIL,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.RECOVER_ACCOUNT,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.RECOVER_ACCOUNT_CONFIRM,
                    ApiConstants.CPORTAL_BASE_URL + CommonConstant.MAPPING_LOGIN_WITHOUT_PASS,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.REFRESH_TOKEN,
                    CommonConstant.MAPPING_CPORTAL_REPORT_BASE_ADDRESS + ApiConstants.FILE_DOWNLOAD,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.ABT_WEB_BUSINESS_SAVE_ORDER_AS_DRAFT,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_USERID_BY_USERNAME,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_USERNAME_BY_USERID,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CUSTOMER_PORTAL_SIGNUP,
                    ApiConstants.CUSTOMER_PORTAL_SIGNUP_CONTROLLER + CommonConstant.SIGN_UP,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.DOWNLOAD_SJT_INVOICE,
                    ApiConstants.TRANSACTION_CONTROLLER + ApiConstants.SEARCH_DATA,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.UPDATE_USER_ACCOUNT_DETAILS,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CHANGE_USER_CITY,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.GET_USER_COUNTRY,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.USER_PROFILE,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CHANGE_USER_COUNTRY,
//                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.UPDATE_SECONDARY_USER_DETAIL_SIGNUP,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.REFRESH_TOKEN_V1,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CHECK_MEDIA_BALANCE,
                    ApiConstants.OPT_VERIFICATION_CONTROLLER + ApiConstants.SEND_OTP,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CPORTAL_VERIFY_EMAIL,
                    ApiConstants.OPT_VERIFICATION_CONTROLLER + ApiConstants.VERIFY_OTP,
                    CommonConstant.WINDCAVE_CALLBACK_CONTROLLER + ApiConstants.CALL_BACK_CANCEL,
                    CommonConstant.WINDCAVE_CALLBACK_CONTROLLER + ApiConstants.CALL_BACK_DECLINED,
                    CommonConstant.WINDCAVE_CALLBACK_CONTROLLER + ApiConstants.CALL_BACK_SUCCESS,
                    CommonConstant.WINDCAVE_CALLBACK_CONTROLLER + ApiConstants.NOTIFY_PAYMENT_GATEWAY,
                    ApiConstants.SKEDGO_CPORTAL_BASE_URL + ApiConstants.GET_ACCOUNT_NO_BY_USERNAME,
                    ApiConstants.SKEDGO_CPORTAL_BASE_URL + CommonConstant.SIGN_UP_SKEDGO,
                    ApiConstants.SKEDGO_CPORTAL_BASE_URL + ApiConstants.SIGN_UP_FRS,
                    CommonConstant.MAPPING_CPORTAL_MASTER_CONTROLLER + ApiConstants.GET_MAX_ACCOUNT_BALANCE_CONFIG,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.CIRCLE_BALANCE_TRANSFER_OWNER_TO_MEMBER_API,
                    ApiConstants.CPORTAL_BASE_URL + ApiConstants.UPDATE_MEMBER_BALANCE,
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/actuator/info",
                    "/actuator/health",
                    "/v3/api-docs/swagger-config"};
            if (Arrays.stream(allowedUrl).anyMatch(s -> pathMatcher.match(s, requestedUrl))) {
                isAllowedAccess = true;
            }
            return isAllowedAccess;
        } catch (Exception e) {
            LOGGER.error("Failed to authenticate request in URL: {}, ErrorMessage:{}", httpRequest.getServletPath(), e.getMessage());
            return isAllowedAccess;
        }
    }
}