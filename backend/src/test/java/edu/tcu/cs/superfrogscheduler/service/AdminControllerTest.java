package edu.tcu.cs.superfrogscheduler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.superfrogscheduler.controller.AdminController;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.system.*;
import edu.tcu.cs.superfrogscheduler.model.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedOptions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(AdminController.class)
@Import({PasswordEncoderConfig.class, SecurityConfig.class})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SuperFrogStudentService studentService;

    @MockBean
    private SuperFrogAppearanceRequestService appearanceRequestService;

    @MockBean
    private SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testCreateSuperFrogStudent_Success_Admin() throws Exception {
        SuperFrogStudent student = new SuperFrogStudent();
        student.setEmail("test@example.com");
        when(studentService.createSuperFrogStudent(any(SuperFrogStudent.class)))
            .thenReturn(new SuperFrogStudent());  // Assume a valid student object is returned

        mockMvc.perform(post("/api/admin/createStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("SuperFrog Student account created successfully")));
    }

    @Test
    @WithMockUser(username="student", roles={"STUDENT"})
    public void testCreateSuperFrogStudent_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/admin/createStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"student@example.com\"}"))
            .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testDeactivateStudent_Success() throws Exception {
        String studentId = "1";
        doNothing().when(studentService).deactivateSuperFrogStudent(studentId, "Graduated");

        mockMvc.perform(post("/api/admin/deactivateStudent/{studentId}", studentId)
                .param("reason", "Graduated")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Student account deactivated successfully.")));
    }


    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testDeactivateStudent_NotFound() throws Exception {
        String studentId = "2";
        doThrow(new IllegalArgumentException("Student not found with ID: " + studentId))
            .when(studentService).deactivateSuperFrogStudent(studentId, "Graduated");

        mockMvc.perform(post("/api/admin/deactivateStudent/{studentId}", studentId)
                .param("reason", "Graduated")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error deactivating account: Student not found with ID: " + studentId)));
    }
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testSearchStudents_Success() throws Exception {
        SuperFrogStudent student = new SuperFrogStudent();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("johndoe@example.com");
        student.setPhoneNumber("(123) 456-7890");
        student.setPhysicalAddress("1234 Elm Street");

        List<SuperFrogStudent> students = Arrays.asList(student);
        when(studentService.findSuperFrogStudents(
                Optional.of("John"),
                Optional.of("Doe"),
                Optional.empty(),
                Optional.empty()))
            .thenReturn(students);
        MvcResult result = mockMvc.perform(get("/api/admin/searchStudents")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Response: " + result.getResponse().getContentAsString());
        verify(studentService).findSuperFrogStudents(
            Optional.of("John"),
            Optional.of("Doe"),
            Optional.empty(),
            Optional.empty()
            );
    }

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testSearchStudents_NoResults() throws Exception {
        when(studentService.findSuperFrogStudents(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admin/searchStudents")
                .param("firstName", "Unknown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]")); // Expecting empty JSON array
    }
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testViewStudentDetails_Success() throws Exception {
        String studentId = "1";
        SuperFrogStudent student = new SuperFrogStudent();
        student.setId(studentId);
        student.setEmail("test@example.com");
        when(studentService.findStudentById(studentId)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/admin/student/{studentId}", studentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testViewStudentDetails_NotFound() throws Exception {
        String studentId = "2";
        when(studentService.findStudentById(studentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admin/student/{studentId}", studentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testReverseAppearanceDecision_Success() throws Exception {
        // Given
        SuperFrogAppearanceRequestDto requestDto = new SuperFrogAppearanceRequestDto(
                1,
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "John",
                "Doe",
                "1234567890",
                "johndoe@example.com",
                "PUBLIC",
                "Annual Fundraiser",
                "Local Charity",
                "1234 Main St, Anytown",
                "Please arrive 15 minutes early",
                "None",
                "No",
                "A community event to raise funds",
                RequestStatus.APPROVED
        );

        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        // When - Set up the existing appearance request for comparison
        SuperFrogAppearanceRequest existingRequest = new SuperFrogAppearanceRequest(
                1,
                requestDto.eventDate(),
                requestDto.startTime(),
                requestDto.endTime(),
                requestDto.contactFirstName(),
                requestDto.contactLastName(),
                requestDto.phoneNumber(),
                requestDto.email(),
                EventType.valueOf(requestDto.eventType()), // Assuming EventType is an enum with values corresponding to the string types
                requestDto.eventTitle(),
                requestDto.nameOfOrg(),
                requestDto.address(),
                requestDto.specialInstructions(),
                requestDto.expenses(),
                requestDto.outsideOrgs(),
                requestDto.description(),
                requestDto.status()
        );

        // Assuming the service method reverses the status and returns the updated request
        when(appearanceRequestService.reverseDecision(anyInt()))
                .thenReturn(existingRequest);

        // When - Then
        this.mockMvc.perform(put("/api/appearance/{requestId}", existingRequest.getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("APPROVED")))  // Assuming the JSON response contains a status field at the root level
                .andExpect(jsonPath("$.data.requestId", is(existingRequest.getRequestId())))
                .andExpect(jsonPath("$.data.eventTitle", is(existingRequest.getEventTitle())))
                .andExpect(jsonPath("$.data.description", is(existingRequest.getDescription())));
    }






}
