package edu.tcu.cs.superfrogscheduler.model;

import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

// customer can request a SuperFrog appearance by submitting a SuperFrogAppearanceRequest
// SuperFrogAppearanceRequest includes event-related information
@Entity
public class SuperFrogAppearanceRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestId;


    // added date, start time, and end time
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String contactFirstName;
    private String contactLastName;
    private String phoneNumber;
    private String email;
    private EventType eventType;
    private String eventTitle;
    private String nameOfOrg;
    private String address;
    private String specialInstructions;
    private String expenses;
    private String outsideOrgs;
    private String description;
    private RequestStatus status;

    private String rejectionReason;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
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

    public EventType getEventType() {
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    private String assignedSuperFrog;

    public SuperFrogAppearanceRequest() {

    }

    public SuperFrogAppearanceRequest(Integer requestId, LocalDate eventDate, LocalTime startTime, LocalTime endTime, String contactFirstName, String contactLastName, String phoneNumber, String email, EventType eventType, String eventTitle, String nameOfOrg, String address, String specialInstructions, String expenses, String outsideOrgs, String description, RequestStatus status) {
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

    public String getAssignedSuperFrog() {
        return assignedSuperFrog;
    }

    public void setAssignedSuperFrog(String assignedSuperFrog) {
        this.assignedSuperFrog = assignedSuperFrog;
    }
}