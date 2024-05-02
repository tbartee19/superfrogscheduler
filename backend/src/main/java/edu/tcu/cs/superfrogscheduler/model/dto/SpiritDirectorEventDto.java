package edu.tcu.cs.superfrogscheduler.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SpiritDirectorEventDto(
        Integer id,
        String eventTitle,
        LocalDate startDate,
        LocalTime startTime,
        LocalDate endDate,
        LocalTime endTime,
        LocalDate recurrenceStart,
        LocalDate recurrenceEnd){

}