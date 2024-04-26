package edu.tcu.cs.superfrogscheduler.system;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tcu.cs.superfrogscheduler.model.Event;
import edu.tcu.cs.superfrogscheduler.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event) {
        List<Event> existingEvents = eventRepository.findByStudentIdAndStartDateTimeBetween(
            event.getStudentId(),
            event.getStartDateTime(),
            event.getEndDateTime());
    
        if (!existingEvents.isEmpty()) {
            throw new IllegalStateException("Conflict detected: An event already exists within the provided timeframe.");
        }
        return eventRepository.save(event);
    }
    

    public Event updateEvent(String eventId, Event updatedEvent) throws Exception {
        Event existingEvent = eventRepository.findById(eventId)
            .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + eventId));

        // Check if the updated event times are changed and if they conflict with other events
        if (!existingEvent.getStartDateTime().equals(updatedEvent.getStartDateTime()) ||
            !existingEvent.getEndDateTime().equals(updatedEvent.getEndDateTime())) {

            // Find any events that overlap the new time frame, excluding the current event
            List<Event> conflictingEvents = eventRepository.findByStudentIdAndStartDateTimeBetween(
                updatedEvent.getStudentId(),
                updatedEvent.getStartDateTime(),
                updatedEvent.getEndDateTime()).stream()
                .filter(event -> !event.getId().equals(eventId)) // Exclude current event
                .collect(Collectors.toList());

            if (!conflictingEvents.isEmpty()) {
                throw new IllegalStateException("Conflict detected: An event already exists within the provided timeframe.");
            }
        }

    // No conflicts, proceed to save the updated event
    return eventRepository.save(updatedEvent);
}


    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<Event> getStudentEvents(String studentId) {
        return eventRepository.findByStudentId(studentId);
    }
}

