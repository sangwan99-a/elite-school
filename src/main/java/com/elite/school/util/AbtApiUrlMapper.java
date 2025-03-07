//package com.elite.school.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class AbtApiUrlMapper {
//
//    private final AbtApiConfig abtApiConfig;
//    @Autowired
//    ClientCredentialHelper clientCredentialHelper;
//
//
//    public String getWindCaveAuthKey() {
//        return abtApiConfig.getWndCaveAuthKey();
//    }
//
//    public AbtApiUrlMapper(AbtApiConfig abtApiConfig) {
//        this.abtApiConfig = abtApiConfig;
//    }
//
//    public String getTmsKey() {
//        return abtApiConfig.getTmsKey();
//    }
//
//    public String getAbtToken() {
//        return clientCredentialHelper.fetchABTAuthTokenHeader().get(CommonConstant.AUTHORIZATION_KEY);
//    }
//
//    public AuthCustomizer tmsAuthCustomizer() {
//        return headers -> headers.set(CommonConstant.AUTHORIZATION_KEY, getTmsKey());
//    }
//
//    public AuthCustomizer windcaveAuthCustomizer() {
//        return headers -> headers.set(CommonConstant.AUTHORIZATION_KEY, getWindCaveAuthKey());
//    }
//
//    public AuthCustomizer abtAuthCustomizer() {
//        return headers -> headers.set(CommonConstant.AUTHORIZATION_KEY, getAbtToken());
//    }
//
//    public void injectAuth(EnumConstant.ModuleUri module, HttpHeaders headers) {
//        if (EnumConstant.ModuleUri.TMS == module) {
//            tmsAuthCustomizer().customize(headers);
//        }
//        if (EnumConstant.ModuleUri.ABT_CUSTOMER_PORTAL == module) {
//            abtAuthCustomizer().customize(headers);
//        }
//        if (EnumConstant.ModuleUri.ABT_WEB_PORTAL == module) {
//            abtAuthCustomizer().customize(headers);
//        }
//        if (EnumConstant.ModuleUri.WINDCAVE == module) {
//            windcaveAuthCustomizer().customize(headers);
//        }
//
//    }
//
//    /**
//     * Returns prefix URL for the given module based on the configuration.
//     * Works well both with cloud and on-premise hosting.
//     */
//    public String getServiceBaseUri(EnumConstant.ModuleUri module) {
//        switch (module) {
//            case COGNITO_ACCESS_MANAGEMENT -> {
//                return abtApiConfig.getCognitoAccessBaseUrl() + module.getValue();
//            }
//            case CACHE_MANAGEMENT -> {
//                return abtApiConfig.getCacheManagementBaseUrl() + module.getValue();
//            }
//            case MASTER_MANGEMENT -> {
//                return abtApiConfig.getMasterManagementBaseUrl() + module.getValue();
//            }
//            case ACCOUNT_MANGEMENT -> {
//                return abtApiConfig.getAccountManagementBaseUrl() + module.getValue();
//            }
//            case FARE_MANAGEMENT -> {
//                return abtApiConfig.getFareManagementBaseUrl() + module.getValue();
//            }
//            case CARD_MANAGEMENT -> {
//                return abtApiConfig.getCardManagementBaseUrl() + module.getValue();
//            }
//            case TRANSACTION_MANAGEMENT -> {
//                return abtApiConfig.getTransactionManagementBaseUrl() + module.getValue();
//            }
//            case NOTIFICATION_MANAGER -> {
//                return abtApiConfig.getNotificationManagerBaseUrl() + module.getValue();
//            }
//            case ABT_CUSTOMER_PORTAL -> {
//                return abtApiConfig.getCustomerOrchestratorBaseUrl() + module.getValue();
//            }
//            case ABT_WEB_PORTAL -> {
//                return abtApiConfig.getOperatorOrchestratorBaseUrl() + module.getValue();
//            }
//            case DEVICE_MANAGEMENT -> {
//                return abtApiConfig.getDeviceManagementBaseUrl() + module.getValue();
//            }
//
//            case TMS -> {
//                return abtApiConfig.getTmsBaseUrl() + module.getValue();
//            }
//            case LITTLE_PAY -> {
//                return abtApiConfig.getLittlePayBaseUrl() + module.getValue();
//            }
//            case MOBILE_APP -> {
//                return abtApiConfig.getMobileAppBaseUrl() + module.getValue();
//            }
//            case ACT_DA -> {
//                return abtApiConfig.getActDaBaseUrl() + module.getValue();
//            }
//            case WINDCAVE -> {
//                return abtApiConfig.getWindCaveBaseUrl() + module.getValue();
//            }
//            case ACT_DA_EXTERNAL_AUTH -> {
//                return abtApiConfig.getActDaExternalAuthBaseUrl() + module.getValue();
//            }
//            case ACT_DA_EXTERNAL -> {
//                return abtApiConfig.getActDaExternalBaseUrl() + module.getValue();
//            }
//        }
//        throw new RuntimeException("no module mapping found for module: " + module.getValue());
//    }
//}