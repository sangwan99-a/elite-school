package com.elite.school.framework.AWS;

@Setter
@Getter
@Configuration
public class AwsConfig {
    @Autowired
    private AbtApiConfig abtApiConfig;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(abtApiConfig.getAwsRegion()))
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();
    }
}
