package com.project.coursesplatformapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coursesplatformapi.model.Review;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void emailSender(String recipientEmail, String subject, Review review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(review);

        System.out.println("Simulating sending email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}
