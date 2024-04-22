package edu.tcu.cs.superfrogscheduler.model.dto;

import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

public record SuperFrogAppearanceRequestDto(


                Integer requestId,
                String contactFirstName,
                String contactLastName,
                String phoneNumber,
                String email,
                String eventType,
                String eventTitle,
                String nameOfOrg,
                String address,
                String isOnTCUCampus,
                String specialInstructions,
                String expenses,
                String outsideOrgs,
                String description,
                RequestStatus status,
                String tcuEventDetails) {


    public RequestStatus getStatus() {

        return null;
    }
    public String getTcuEventDetails() {
        return tcuEventDetails;
    }

}
