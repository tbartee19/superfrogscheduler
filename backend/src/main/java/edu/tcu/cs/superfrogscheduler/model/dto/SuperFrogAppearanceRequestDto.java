package edu.tcu.cs.superfrogscheduler.model.dto;

import edu.tcu.cs.superfrogscheduler.model.EventType;

public record SuperFrogAppearanceRequestDto(
        Integer requestId,
        String contactFirstName,
        String contactLastName,
        String contactPhoneNumber,
        String email,
        EventType eventType,
        String eventTitle,
        String nameOfOrg,
        String address,
        String isOnTCUCampus,
        String specialInstructions,
        String expenses,
        String outsideOrgs,
        String description,
        SuperFrogStudentDto student
) {

}
