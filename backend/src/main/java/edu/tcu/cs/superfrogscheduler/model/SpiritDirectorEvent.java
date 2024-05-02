package edu.tcu.cs.superfrogscheduler.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpiritDirectorEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String eventTitle;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private LocalDate recurrenceStart;
    private LocalDate recurrenceEnd;

    public Integer getId() {
        return this.id;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getRecurrenceStart() {
        return this.recurrenceStart;
    }

    public void setRecurrenceStart(LocalDate recurrenceStart) {
        this.recurrenceStart = recurrenceStart;
    }

    public LocalDate getRecurrenceEnd() {
        return this.recurrenceEnd;
    }

    public void setRecurrenceEnd(LocalDate recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
    }
}