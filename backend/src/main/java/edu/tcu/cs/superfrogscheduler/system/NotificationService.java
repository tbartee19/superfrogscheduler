package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyApproval(SuperFrogAppearanceRequest request) {
        // Send email or internal message notification
        System.out.println("Notification sent for approved request: " + request.getRequestId());
    }

    public void notifyRejection(SuperFrogAppearanceRequest request, String reason) {
        // Send email or internal message notification
        System.out.println("Notification sent for rejected request: " + request.getRequestId() + " with reason: " + reason);
    }
}