package edu.tcu.cs.superfrogscheduler.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogStudentService;
import edu.tcu.cs.superfrogscheduler.model.dto.ProfileUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SuperFrogStudentServiceTest {

    @Mock
    private SuperFrogStudentRepository repository;

    @InjectMocks
    private SuperFrogStudentService studentService;

    private SuperFrogStudent student;
    private ProfileUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        // Create a sample student
        student = new SuperFrogStudent();
        student.setId("1");
        student.setFirstName("OldFirstName");
        student.setLastName("OldLastName");
        student.setPhoneNumber("(123) 456-7890");
        student.setEmail("oldemail@example.com");
        student.setPhysicalAddress("123 Old St");
        student.setInternationalStudent(false);
        student.setPaymentPreference("Mail Check");

        // Create a DTO with new data
        updateDTO = new ProfileUpdateDTO();
        updateDTO.setFirstName("NewFirstName");
        updateDTO.setLastName("NewLastName");
        updateDTO.setPhoneNumber("(321) 654-9870");
        updateDTO.setEmail("oldemail@example.com");
        updateDTO.setPhysicalAddress("321 New St");
        updateDTO.setInternationalStudent(true);
        updateDTO.setPaymentPreference("Mail Check");
    }

    @Test
    void testUpdateStudentProfile_Success() {
        try {
            // Arrange
            when(repository.findByEmail("oldemail@example.com")).thenReturn(Optional.of(student));
            doNothing().when(studentService).validateStudentProfile(student);
            when(repository.save(any(SuperFrogStudent.class))).thenReturn(student);
    
            // Act
            SuperFrogStudent updatedStudent = studentService.updateStudentProfile("oldemail@example.com", updateDTO);
    
            // Assert
            assertNotNull(updatedStudent);
            assertEquals("NewFirstName", updatedStudent.getFirstName());
            assertEquals("NewLastName", updatedStudent.getLastName());
            assertEquals("(321) 654-9870", updatedStudent.getPhoneNumber());
            assertEquals("newemail@example.com", updatedStudent.getEmail());
            verify(repository, times(1)).save(any(SuperFrogStudent.class));
            verify(studentService, times(1)).notifyProfileUpdate(updatedStudent);
        } catch (Exception e) {
            fail("No exception should be thrown in successful update",e);
        }
    }

    @Test
    void testUpdateStudentProfile_NotFound() {
        // Arrange
        when(repository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudentProfile("unknown@example.com", updateDTO);
        });

        assertEquals("Student not found.", exception.getMessage());
    }
}
