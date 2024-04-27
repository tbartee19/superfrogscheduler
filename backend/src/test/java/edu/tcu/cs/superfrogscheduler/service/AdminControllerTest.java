package edu.tcu.cs.superfrogscheduler.service;

import edu.tcu.cs.superfrogscheduler.controller.AdminController;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.*;
import edu.tcu.cs.superfrogscheduler.model.*;
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

    @MockBean
    private SuperFrogStudentService studentService;

    @MockBean
    private SuperFrogAppearanceRequestService appearanceRequestService;

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
    public void testReverseApprovedDecision_Success() throws Exception{
        SuperFrogAppearanceRequest existingRequest = new SuperFrogAppearanceRequest();
        existingRequest.setStatus(RequestStatus.APPROVED);
        existingRequest.setRequestId(1);

        when(appearanceRequestService.reverseDecision(existingRequest.getRequestId())).thenReturn(existingRequest);

        SuperFrogAppearanceRequestDto updatedRequestDto = new SuperFrogAppearanceRequestDto();
        updatedRequestDto.setRequestId(existingRequest.getRequestId());
        updatedRequestDto.setStatus("REJECTED");  // Assuming DTO uses String for status
        when(superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(existingRequest))
                .thenReturn(updatedRequestDto);

        // Perform the PUT request and check responses
        mockMvc.perform(put("/api/appearance/{requestId}", existingRequest.getRequestId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status", is("REJECTED")));  // Correct JSON path if necessary

    }






}
