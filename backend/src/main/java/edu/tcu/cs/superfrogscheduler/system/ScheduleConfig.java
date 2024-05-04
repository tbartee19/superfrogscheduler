package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private SuperFrogAppearanceRequestService requestService;

    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void remindDirectorToApproveRequests() {
        List<SuperFrogAppearanceRequest> pendingRequests = requestService.findByStatus(RequestStatus.PENDING);
        if (!pendingRequests.isEmpty()) {
            System.out.println("Reminder: There are " + pendingRequests.size() + " pending requests to be reviewed.");
            // Optional: Implement notification logic here
        }
    }

}
