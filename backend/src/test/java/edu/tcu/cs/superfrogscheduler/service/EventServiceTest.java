package edu.tcu.cs.superfrogscheduler.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tcu.cs.superfrogscheduler.model.Event;
import edu.tcu.cs.superfrogscheduler.repository.EventRepository;
import edu.tcu.cs.superfrogscheduler.system.EventService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId("1");
        event.setStudentId("student1");
        event.setTitle("Test Event");
        event.setStartDateTime(LocalDateTime.now());
        event.setEndDateTime(LocalDateTime.now().plusHours(2));
    }

    @Test
    void addEvent_Success() {
        when(eventRepository.findByStudentIdAndStartDateTimeBetween(
                anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());  // No conflicts
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventService.addEvent(event);

        assertNotNull(savedEvent);
        verify(eventRepository).save(event);
    }

    @Test
    void addEvent_ConflictDetected() {
        when(eventRepository.findByStudentIdAndStartDateTimeBetween(
                anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of(event));  // Existing event causes conflict

        assertThrows(IllegalStateException.class, () -> eventService.addEvent(event));
    }

    @Test
    void updateEvent_Success() throws Exception {
    // Arrange: set up an updated event with different times
    Event updatedEvent = new Event();
    updatedEvent.setId(event.getId());
    updatedEvent.setStudentId(event.getStudentId());
    updatedEvent.setTitle(event.getTitle());
    updatedEvent.setStartDateTime(event.getStartDateTime().plusDays(1));  // Change the time
    updatedEvent.setEndDateTime(event.getEndDateTime().plusDays(1));  // Change the time

    when(eventRepository.findById(anyString())).thenReturn(Optional.of(event));
    when(eventRepository.findByStudentIdAndStartDateTimeBetween(
            anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
        .thenReturn(Collections.emptyList());
    when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

    // Act
    Event resultEvent = eventService.updateEvent("1", updatedEvent);

    // Assert
    assertNotNull(resultEvent);
    assertEquals(updatedEvent.getStartDateTime(), resultEvent.getStartDateTime());
    assertEquals(updatedEvent.getEndDateTime(), resultEvent.getEndDateTime());
    verify(eventRepository).findByStudentIdAndStartDateTimeBetween(
            updatedEvent.getStudentId(),
            updatedEvent.getStartDateTime(),
            updatedEvent.getEndDateTime());
    verify(eventRepository).save(updatedEvent);
}


    @Test
    void updateEvent_NotFound() {
        when(eventRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> eventService.updateEvent("1", event));
    }

    @Test
    void deleteEvent_Success() {
        doNothing().when(eventRepository).deleteById(anyString());

        assertDoesNotThrow(() -> eventService.deleteEvent("1"));
        verify(eventRepository).deleteById("1");
    }

    @Test
    void getStudentEvents_Success() {
        when(eventRepository.findByStudentId(anyString())).thenReturn(List.of(event));

        List<Event> events = eventService.getStudentEvents("student1");

        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        verify(eventRepository).findByStudentId("student1");
    }

}
