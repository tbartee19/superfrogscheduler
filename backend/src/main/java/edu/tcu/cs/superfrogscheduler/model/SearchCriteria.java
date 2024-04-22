package edu.tcu.cs.superfrogscheduler.model;

import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import java.time.LocalDateTime;

public class SearchCriteria {
    private String eventTitle;
    private LocalDateTime eventDate;
    private String contactName; // Combined first and last name search
    private RequestStatus status;

    // Getters and setters
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
