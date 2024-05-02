package edu.tcu.cs.superfrogscheduler.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.tcu.cs.superfrogscheduler.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByStudentId(String studentId);
    List<Event> findByStudentIdAndStartDateTimeBetween(String studentId, LocalDateTime start, LocalDateTime end);
}