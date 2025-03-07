//package com.elite.school.framework.AWS;
//
//import com.elite.school.util.AbtApiConfig;
//import jdk.internal.foreign.abi.Binding;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Setter
//@Getter
//@Configuration
//public class AwsConfig<S3Client> {
//    @Autowired
//    private AbtApiConfig abtApiConfig;
//
//    @Bean
//    public S3Client s3Client() {
//        Binding UrlConnectionHttpClient = null;
//        return S3Client.builder()
//                .region(Region.of(abtApiConfig.getAwsRegion()))
//                .httpClient(UrlConnectionHttpClient.builder().build())
//                .build();
//    }
//}
