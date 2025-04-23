package com.project.coursesplatformapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coursesplatformapi.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.abbreviate;

@Service
@Slf4j
public class NotificationService {

    public void emailSender(String recipientEmail, String subject, Review review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(review);

        log.info("Sending email | To: {} | Subject: {} | Content preview: {}", recipientEmail, subject, abbreviate(message, 100));
    }
}
