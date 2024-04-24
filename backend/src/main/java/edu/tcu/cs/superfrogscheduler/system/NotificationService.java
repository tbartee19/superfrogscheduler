package edu.tcu.cs.superfrogscheduler.system;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String message) {
        // Logic to send email or SMS
        System.out.println("Notification sent with message: " + message);
    }
}
