package edu.tcu.cs.superfrogscheduler.model;

import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

// customer can request a SuperFrog appearance by submitting a SuperFrogAppearanceRequest
// SuperFrogAppearanceRequest includes event-related information
@Entity
public class SuperFrogAppearanceRequest {
    // likely will need to add date and time of the event to this class

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestId;

    @NotBlank(message = "First name is required")
    private String contactFirstName;
    @NotBlank(message = "Last name is required")
    private String contactLastName;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{4}", message = "Phone number must be in the format (999) 999-9999")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Event type is required")
    private EventType eventType;
    @NotBlank(message = "Event title is required")
    private String eventTitle;
    @NotBlank(message = "Name of organization is required")
    private String nameOfOrg;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Must specify whether event is on TCU campus")
    private String isOnTCUCampus;
    private String specialInstructions;
    private String expenses;
    private String outsideOrgs;
    @NotBlank(message = "Must provide a detailed event description")
    private String description;
    private RequestStatus status;

    private String reason;

    private String EventDetails;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDate eventDate;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotNull(message = "Event type is required") EventType getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        if (eventType.equals("TCU")) {
            this.eventType = EventType.TCU;
        } else if (eventType.equals("PRIVATE")) {
            this.eventType = EventType.PRIVATE;
        } else if (eventType.equals("PUBLIC")) {
            this.eventType = EventType.PUBLIC;
        }
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getNameOfOrg() {
        return nameOfOrg;
    }

    public void setNameOfOrg(String nameOfOrg) {
        this.nameOfOrg = nameOfOrg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getOutsideOrgs() {
        return outsideOrgs;
    }

    public void setOutsideOrgs(String outsideOrgs) {
        this.outsideOrgs = outsideOrgs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public SuperFrogAppearanceRequest() {

    }

    public SuperFrogAppearanceRequest(Integer requestId, LocalDate eventDate, LocalTime startTime, LocalTime endTime,
                                      String contactFirstName, String contactLastName, String phoneNumber, String email,
                                      EventType eventType, String eventTitle, String nameOfOrg, String address,
                                      String specialInstructions, String expenses, String outsideOrgs, String description,
                                      RequestStatus status) {
        this.requestId = requestId;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.eventType = eventType;
        this.eventTitle = eventTitle;
        this.nameOfOrg = nameOfOrg;
        this.address = address;
        this.specialInstructions = specialInstructions;
        this.expenses = expenses;
        this.outsideOrgs = outsideOrgs;
        this.description = description;
        this.status = status;
    }



    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {

        this.endTime = endTime;
    }

    public String getRejectionReason() {

        return reason;
    }

    public void setRejectionReason(String rejectionReason) {

        this.reason = rejectionReason;
    }

}