package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private SuperFrogAppearanceRequestRepository requestRepository;

    @Autowired
    private NotificationService notificationService;  // Assuming you have a service for handling notifications

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminder() {
        long count = requestRepository.countByStatus(RequestStatus.PENDING);
        if (count > 0) {
            String message = "You have " + count + " pending SuperFrog appearance requests to review.";
            notificationService.sendNotification(message);  // Method to send notification to the Spirit Director
            System.out.println("Reminder sent: " + message);
        } else {
            System.out.println("No pending requests to remind.");
        }
    }
}

