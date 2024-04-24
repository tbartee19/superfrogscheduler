package edu.tcu.cs.superfrogscheduler.service;

import edu.tcu.cs.superfrogscheduler.repository.*;
import edu.tcu.cs.superfrogscheduler.system.*;
import edu.tcu.cs.superfrogscheduler.model.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import({PasswordEncoderConfig.class, SecurityConfig.class})
public class SuperFrogAdminServiceTest {

    @Mock
    private SuperFrogStudentRepository studentRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SuperFrogStudentService service;
    
    
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testCreateSuperFrogStudent_Success_Admin() {
        Account account = new Account("test@example.com", "passwordHash", "SUPERFROG_STUDENT");
        SuperFrogStudent student = new SuperFrogStudent();
        student.setEmail("test@example.com");
        student.setAccount(account);

        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(studentRepository.save(any(SuperFrogStudent.class))).thenReturn(student);

        SuperFrogStudent createdStudent = service.createSuperFrogStudent(student);

        assertNotNull(createdStudent);
        assertEquals("encodedPassword", createdStudent.getAccount().getPasswordHash());
        verify(accountRepository, times(1)).save(account);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testDeactivateSuperFrogStudent_Success() {
        // Given
        String studentId = "studentId";
        Account account = new Account("email@example.com", "hashedPassword", "SUPERFROG_STUDENT");
        account.setActive(true);
        SuperFrogStudent student = new SuperFrogStudent();
        student.setId(studentId);
        student.setAccount(account);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(accountRepository.save(account)).thenReturn(account);

        // When
        service.deactivateSuperFrogStudent(studentId, "Retirement");

        // Then
        assertFalse(student.getAccount().isActive());
        verify(accountRepository, times(1)).save(account);
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testDeactivateSuperFrogStudent_AlreadyDeactivated() {
        // Given
        String studentId = "studentId";
        Account account = new Account("email@example.com", "hashedPassword", "SUPERFROG_STUDENT");
        account.setActive(false);
        SuperFrogStudent student = new SuperFrogStudent();
        student.setId(studentId);
        student.setAccount(account);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // When
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.deactivateSuperFrogStudent(studentId, "Retirement");
        });

        // Then
        assertEquals("Account is already deactivated.", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testDeactivateSuperFrogStudent_StudentNotFound() {
        // Given
        String studentId = "studentId";

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deactivateSuperFrogStudent(studentId, "Retirement");
        });

        // Then
        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
public void testFindSuperFrogStudents_Success() {
    // Given
    SuperFrogStudent student1 = new SuperFrogStudent();
    student1.setId("1");
    student1.setFirstName("John");
    student1.setLastName("Doe");
    student1.setEmail("johndoe@example.com");
    student1.setPhoneNumber("(123) 456-7890");

    SuperFrogStudent student2 = new SuperFrogStudent();
    student2.setId("2");
    student2.setFirstName("Jane");
    student2.setLastName("Doe");
    student2.setEmail("janedoe@example.com");
    student2.setPhoneNumber("(123) 456-7890");

    // Make sure the email for Jane is correct in both the object and the stub
    when(studentRepository.findByFirstNameAndLastNameAndPhoneNumberAndEmail("John", "Doe", "(123) 456-7890", "johndoe@example.com"))
            .thenReturn(Arrays.asList(student1));
    when(studentRepository.findByFirstNameAndLastNameAndPhoneNumberAndEmail("Jane", "Doe", "(123) 456-7890", "janedoe@example.com"))
            .thenReturn(Arrays.asList(student2));

    // When
    List<SuperFrogStudent> resultJohn = service.findSuperFrogStudents(Optional.of("John"), Optional.of("Doe"), Optional.of("(123) 456-7890"), Optional.of("johndoe@example.com"));
    List<SuperFrogStudent> resultJane = service.findSuperFrogStudents(Optional.of("Jane"), Optional.of("Doe"), Optional.of("(123) 456-7890"), Optional.of("janedoe@example.com"));

    // Then
    assertNotNull(resultJohn);
    assertEquals(1, resultJohn.size());
    assertEquals("John", resultJohn.get(0).getFirstName());

    assertNotNull(resultJane);
    assertEquals(1, resultJane.size());
    assertEquals("Jane", resultJane.get(0).getFirstName());

    verify(studentRepository, times(1)).findByFirstNameAndLastNameAndPhoneNumberAndEmail("John", "Doe", "(123) 456-7890", "johndoe@example.com");
    verify(studentRepository, times(1)).findByFirstNameAndLastNameAndPhoneNumberAndEmail("Jane", "Doe", "(123) 456-7890", "janedoe@example.com");
}


    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testFindSuperFrogStudents_NoResults() {
        // Given
        when(studentRepository.findByFirstNameAndLastNameAndPhoneNumberAndEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList());

        // When
        List<SuperFrogStudent> results = service.findSuperFrogStudents(
            Optional.of("Nonexistent"), 
            Optional.of("Student"), 
            Optional.of("(000) 000-0000"), 
            Optional.of("nobody@example.com"));

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(studentRepository, times(1)).findByFirstNameAndLastNameAndPhoneNumberAndEmail("Nonexistent", "Student", "(000) 000-0000", "nobody@example.com");
    }
    @Test
    @WithMockUser(username="spiritdirector", roles={"ADMIN"})
    public void testViewStudentDetails_Success() {
        String studentId = "someStudentId";
        SuperFrogStudent expectedStudent = new SuperFrogStudent();
        expectedStudent.setId(studentId);
        expectedStudent.setFirstName("John");
        expectedStudent.setLastName("Doe");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        Optional<SuperFrogStudent> actualStudent = service.findStudentById(studentId);

        assertTrue(actualStudent.isPresent());
        assertEquals(expectedStudent.getId(), actualStudent.get().getId());
        verify(studentRepository).findById(studentId);
    }

}



