package com.secke.user_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secke.user_service.Model.User;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String USER_REGISTRATION_TOPIC = "user-registration";

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserRegistrationEvent(User user) {
        try {
            // Convert the User object to a JSON string
            String userJson = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(USER_REGISTRATION_TOPIC, userJson);
            System.out.println("User registration event sent: " + userJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.err.println("Failed to serialize User object: " + e.getMessage());
        }
    }
}

