package edu.tcu.cs.superfrogscheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.tcu.cs.superfrogscheduler.model.Event;
import edu.tcu.cs.superfrogscheduler.system.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        System.out.println(event);
        try {
            Event savedEvent = eventService.addEvent(event);
            return ResponseEntity.ok(savedEvent);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable String eventId, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEvent(eventId, event);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        System.out.println("Reached controller deleting event:");
        System.out.print(eventId);
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetch/student/{studentId}")
    public ResponseEntity<List<Event>> getStudentEvents(@PathVariable String studentId) {
        List<Event> events = eventService.getStudentEvents(studentId);
        return ResponseEntity.ok(events);
    }
}

