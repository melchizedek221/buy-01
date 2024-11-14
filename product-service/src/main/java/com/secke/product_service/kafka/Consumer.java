package com.secke.product_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "user-registration", groupId = "user-service-group")
    public void handleUserRegistrationEvent(String user) {
        System.out.println("Received user registration event: " + user);
        
    }
}
