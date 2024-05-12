package com.project.coursesplatformapi.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void emailSender(String recipientEmail, String subject, String message) {
        System.out.println("Simulating sending email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}
