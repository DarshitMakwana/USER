package com.userservice.userservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "userAuditLog", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
