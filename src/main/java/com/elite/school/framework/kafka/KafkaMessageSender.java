package com.elite.school.framework.kafka;

@Service
public class KafkaMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageSender.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private AbtApiConfig abtApiConfig;


    public void sendMessage(String topicName, String message) {
        LOGGER.info("Sending message to topic {}", topicName);
        sendMessageToTopic(topicName, message);
    }

    public void sendFundingSourceEvent(String message) {
        String topicName = abtApiConfig.getTopicTapemv();
        sendMessage(topicName, message);
    }

    public void sendFundingEvent(String message) {
        String topicName = abtApiConfig.getTopicTapemv();
        sendMessage(topicName, message);
    }

    public void sendTransactionEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.EMV_TAP.getValue();
        sendMessage(topicName, message);
    }

    public void sendEmvChargeToLittlepayTopic(String message) {
        String topicName = EnumConstant.KafkaTopic.EMV_CHARGE_SEND.getValue();
        sendMessage(topicName, message);
    }



    public void sendUploadCard(String message) {
        String topicName = EnumConstant.KafkaTopic.ABT_UPLOAD_CARD_INVENTORY.getValue();
        sendMessage(topicName, message);
    }

    public void sendChargeAuthorizationEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.CHARGE_CREATED.getValue();
        sendMessage(topicName, message);
    }

    public void sendChargeCreatedEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.CHARGE_CREATED.getValue();
        sendMessage(topicName, message);
    }

    public void sendChargeSettlementStatusEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.CHARGE_CREATED.getValue();
        sendMessage(topicName, message);
    }

    public void sendRefundStatusEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.EMV_REFUND_STATUS.getValue();
        sendMessage(topicName, message);
    }

    public void sendDebtStatusEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.CHARGE_CREATED.getValue();
        sendMessage(topicName, message);
    }

    public void sendCardStatusEvent(String message) {
        String topicName = EnumConstant.KafkaTopic.CARD_STATUS.getValue();
        sendMessage(topicName, message);
    }

    public void sendFareCapStatusEvent(String message) {
        String topicName = abtApiConfig.getTopicTapemv();
        sendMessage(topicName, message);
    }

    public void sendInspectionReceivedEvent(String message) {
        String topicName = abtApiConfig.getTopicTapemv();
        sendMessage(topicName, message);
    }

    public void sendLowBalanceAlertNotification(String message) {
        String topicName = EnumConstant.KafkaTopic.ABT_LOW_BALANCE.getValue();
        sendMessage(topicName, message);
    }

    public void sendMessageScreenEvents(String message) {
        String topicName = EnumConstant.KafkaTopic.ABT_NOTIFICATION.getValue();
        sendMessageToTopic(topicName, message);
    }
    public void sendAutoTopUpExpiryNotification(String message) {
        String topicName = EnumConstant.KafkaTopic.ABT_AUTO_TOP_UP_CARD_EXPIRY.getValue();
        sendMessageToTopic(topicName, message);
    }

    public void sendFailedMessageToTopic(String message) {
        String topicName = EnumConstant.KafkaTopic.EMV_DEAD_LOCK.getValue();
        sendMessage(topicName, message);
    }

    public void sendPushNotificationMessageToTopic(String message) {
        String topicName = EnumConstant.KafkaTopic.ABT_PUSH_NOTIFICATION.getValue();
        sendMessage(topicName, message);
    }

    public void sendAutoTopUpToTopic(String message) {
        String topicName = EnumConstant.KafkaTopic.PROCESS_AUTO_TOPUP.getValue();
        sendMessageToTopic(topicName, message);
    }

    private void sendMessageToTopic(String topicName, String message) {
        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Message sent successfully. Topic: {}, Offset: {}, Message: {}",
                        topicName,
                        result.getRecordMetadata().offset(),
                        message);
            } else {
                LOGGER.error("Failed to send message to topic: {}. Message: {}, Error: {}",
                        topicName,
                        message,
                        ex.getMessage(),
                        ex);
            }
        });
    }




}