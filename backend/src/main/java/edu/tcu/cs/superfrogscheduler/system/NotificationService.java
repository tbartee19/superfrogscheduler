package edu.tcu.cs.superfrogscheduler.system;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String message, String recipientEmail) {
        // Actual email/SMS sending logic would be here
        System.out.println("Notification sent to " + recipientEmail + ": " + message);
    }
}