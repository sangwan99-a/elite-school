//package com.elite.school.framework.kafka;
//
//import com.elite.school.util.AbtApiConfig;
//import jakarta.websocket.SendResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaMessageSender {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageSender.class);
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    private AbtApiConfig abtApiConfig;
//
//
//    public void sendMessage(String topicName, String message) {
//        LOGGER.info("Sending message to topic {}", topicName);
//        sendMessageToTopic(topicName, message);
//    }
//
//
//    private void sendMessageToTopic(String topicName, String message) {
//        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                LOGGER.info("Message sent successfully. Topic: {}, Offset: {}, Message: {}",
//                        topicName,
//                        result.getRecordMetadata().offset(),
//                        message);
//            } else {
//                LOGGER.error("Failed to send message to topic: {}. Message: {}, Error: {}",
//                        topicName,
//                        message,
//                        ex.getMessage(),
//                        ex);
//            }
//        });
//    }
//
//
//
//
//}