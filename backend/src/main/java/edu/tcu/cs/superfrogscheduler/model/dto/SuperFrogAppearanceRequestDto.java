package edu.tcu.cs.superfrogscheduler.model.dto;

import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
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

    private void updateExistingRequestFromDto(SuperFrogAppearanceRequest existingRequest, SuperFrogAppearanceRequestDto requestDto) {
        existingRequest.setContactFirstName(requestDto.contactFirstName());
        existingRequest.setContactLastName(requestDto.contactLastName());
        existingRequest.setPhoneNumber(requestDto.phoneNumber());
        existingRequest.setEmail(requestDto.email());
        existingRequest.setEventType(requestDto.eventType());
        existingRequest.setEventTitle(requestDto.eventTitle());
        existingRequest.setNameOfOrg(requestDto.nameOfOrg());
        existingRequest.setAddress(requestDto.address());
        existingRequest.setSpecialInstructions(requestDto.specialInstructions());
        existingRequest.setExpenses(requestDto.expenses());
        existingRequest.setOutsideOrgs(requestDto.outsideOrgs());
        existingRequest.setDescription(requestDto.description());
        // Update additional fields as necessary
    }


}