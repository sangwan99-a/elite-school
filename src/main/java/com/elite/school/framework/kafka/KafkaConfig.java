package com.elite.school.framework.kafka;

/*
 *@author sunil1
 * create kafka topic configuration
 * */
@Configuration
@ConditionalOnProperty(name = "kafka.listener.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;


    @Bean
    public KafkaAdmin admin()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic emvTap()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.EMV_TAP.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic chargeAuthorizationStatus()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.CHARGE_AUTHORIZATION_STATUS.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic abtUploadCardInventory()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_UPLOAD_CARD_INVENTORY.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic chargeSettlementStatus()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.CHARGE_SETTLEMENT_STATUS.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic debtStatus()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.DEBT_STATUS.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }



    @Bean
    public NewTopic chargeCreated()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.CHARGE_CREATED.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic abtNotification()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_NOTIFICATION.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic abtAutoTopupExpiry()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_AUTO_TOP_UP_CARD_EXPIRY.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic abtTransaction()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_TAP_TRANSACTION.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic abtLowBalance()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_LOW_BALANCE.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic cardStatus()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.CARD_STATUS.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic refundStatus()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.EMV_REFUND_STATUS.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic pushNotification()
    {
        return TopicBuilder.name(EnumConstant.KafkaTopic.ABT_PUSH_NOTIFICATION.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic autoTopProcess(){
        return TopicBuilder.name(EnumConstant.KafkaTopic.PROCESS_AUTO_TOPUP.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic emvSendCharge(){
        return TopicBuilder.name(EnumConstant.KafkaTopic.EMV_CHARGE_SEND.getValue())
                .partitions(1)
                .replicas(2)
                .build();
    }


}
