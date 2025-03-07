package com.elite.school.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "abt.api")
public class AbtApiConfig {

    private String cacheManagementBaseUrl;
    private String masterManagementBaseUrl;
    private String fareManagementBaseUrl;
    private String accountManagementBaseUrl;
    private String cardManagementBaseUrl;
    private String deviceManagementBaseUrl;
    private String transactionManagementBaseUrl;
    private String notificationManagerBaseUrl;
    private String customerOrchestratorBaseUrl;
    private String operatorOrchestratorBaseUrl;
    private String cognitoAccessBaseUrl;
    private String customerPortalBaseUrl;
    private String operatorPortalBaseUrl;
    private String mobileAppBaseUrl;
    private String tripgoKey;
    //Digital Account base Url
    private String actDaBaseUrl;

    private String tmsBaseUrl;
    private String littlePayBaseUrl;
    private String littlePayClientId;
    private String littlePayClientSecret;
    private String littlePayAudience;
    private String littlePayGrantType;
    private String littlePayParticipantId;
    private String littlePayRequestAccess;


    // ABT Internal Client credential
    private String abtInternalBaseUrl;
    private String abtInternalClientId;
    private String abtInternalClientSecret;
    private String abtInternalGrantType;
    private String abtInternalScope;


    private String windCaveBaseUrl;
    private String wndCaveAuthKey;




    private String openamServerUrl;
    private String openamUsername;
    private String openamPassword;

    private String sftpHost;
    private String sftpPort;
    private String sftpUsername;
    private String sftpPassword;
    private String sftpOsType;
    private String tmsKey;

    private String awsRegion;
    private String s3Bucket;


    private String awsGatewayHost;

    private String topicTapemv;
    private String topicNotification;
    private String kafkaGroupId;

    //Digital External Account base Url
    private String actDaExternalAuthBaseUrl;
    private  String actDAExternalAuthClientId;

    private String actDAExternalAuthClientSecret;
    private String actDAExternalAuthGrantType;

    private String actDaExternalBaseUrl;
    //Digital External Auth Token
    private String actDaTokenRedirectUri;

    private String mobilityConsoleRedirectUri;

    // QR Certificates
    private String qrCertificatePublicKey;
    private String qrCertificatePrivateKey;
    private String abtGoogleRecaptchaV2SecretKey;
}
