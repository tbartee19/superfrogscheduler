//package edu.tcu.cs.superfrogscheduler.service;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.containsString;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import edu.tcu.cs.superfrogscheduler.controller.EventController;
//import edu.tcu.cs.superfrogscheduler.model.Event;
//import edu.tcu.cs.superfrogscheduler.system.*;
//
//@WebMvcTest(EventController.class)
//@Import({PasswordEncoderConfig.class, SecurityConfig.class, JacksonConfig.class})
//public class EventControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private EventService eventService;
//
//    private Event event;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @BeforeEach
//    void setUp() {
//        event = new Event();
//        event.setId("1");
//        event.setStudentId("student1");
//        event.setTitle("Test Event");
//        event.setStartDateTime(LocalDateTime.now());
//        event.setEndDateTime(LocalDateTime.now().plusHours(2));
//    }
//
//    @Test
//    @WithMockUser(username="student", roles={"STUDENT"})
//    public void createEvent_Success() throws Exception {
//        when(eventService.addEvent(any(Event.class))).thenReturn(event);
//
//        mockMvc.perform(post("/api/events")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(event)))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.title", is(event.getTitle())));
//    }
//    @Test
//    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
//    public void accessDeniedForAdmin_CreateEvent() throws Exception {
//        mockMvc.perform(post("/api/events")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(event)))
//            .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username="student", roles={"STUDENT"})
//    public void createEvent_Conflict() throws Exception {
//        when(eventService.addEvent(any(Event.class))).thenThrow(new IllegalStateException("Conflict detected"));
//
//        mockMvc.perform(post("/api/events")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(event)))
//            .andExpect(status().isConflict())
//            .andExpect(content().string(containsString("Conflict detected")));
//    }
//
//    @Test
//    @WithMockUser(username="student", roles={"STUDENT"})
//    public void updateEvent_Success() throws Exception {
//        when(eventService.updateEvent(eq("1"), any(Event.class))).thenReturn(event);
//
//        mockMvc.perform(put("/api/events/{eventId}", "1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(event)))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", is(event.getId())));
//    }
//
//    @Test
//    @WithMockUser(username="student", roles={"STUDENT"})
//    public void deleteEvent_Success() throws Exception {
//        doNothing().when(eventService).deleteEvent(anyString());
//
//        mockMvc.perform(delete("/api/events/{eventId}", "1")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk());
//    }
//
//
//    @Test
//    @WithMockUser(username="student", roles={"STUDENT"})
//    public void getStudentEvents_Success() throws Exception {
//        when(eventService.getStudentEvents(anyString())).thenReturn(List.of(event));
//
//        mockMvc.perform(get("/api/events/student/{studentId}", "student1")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].studentId", is(event.getStudentId())));
//    }
//
//}
//
