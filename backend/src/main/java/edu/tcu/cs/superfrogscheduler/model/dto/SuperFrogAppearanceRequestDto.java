package edu.tcu.cs.superfrogscheduler.model.dto;

import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record SuperFrogAppearanceRequestDto(
                Integer requestId,
                LocalDate eventDate,
                LocalTime startTime,
                LocalTime endTime,
                String contactFirstName,
                String contactLastName,
                String phoneNumber,
                String email,
                String eventType,
                String eventTitle,
                String nameOfOrg,
                String address,
                String specialInstructions,
                String expenses,
                String outsideOrgs,
                String description,
                RequestStatus status) {

    public RequestStatus getStatus() {

        return null;
    }
}
