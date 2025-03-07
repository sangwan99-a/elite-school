//package com.elite.school.framework.kafka;
//
//import lombok.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///*
// *@author sunil1
// * create kafka topic configuration
// * */
//@Configuration
//@ConditionalOnProperty(name = "kafka.listener.enabled", havingValue = "true", matchIfMissing = false)
//public class KafkaConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String kafkaServer;
//
//
//    @Bean
//    public KafkaAdmin admin()
//    {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(
//                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
//                kafkaServer);
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public NewTopic emvTap()
//    {
//        return TopicBuilder.name(EnumConstant.KafkaTopic.EMV_TAP.getValue())
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//
//
//}
