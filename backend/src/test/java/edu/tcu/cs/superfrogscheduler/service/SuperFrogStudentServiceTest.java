package edu.tcu.cs.superfrogscheduler.service;

import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogStudentService;
import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;


// working on unit test for use case 13
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SuperFrogStudentServiceTest {

    @Mock
    private SuperFrogStudentRepository studentRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SuperFrogStudentService service;

    @Test
    public void testCreateSuperFrogStudent_Success() {
        // Given
        Account account = new Account("test@example.com", "passwordHash", "SUPERFROG_STUDENT");
        SuperFrogStudent student = new SuperFrogStudent();
        student.setEmail("test@example.com");
        student.setAccount(account);

        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(studentRepository.save(any(SuperFrogStudent.class))).thenReturn(student);

        // When
        SuperFrogStudent createdStudent = service.createSuperFrogStudent(student);

        // Then
        assertNotNull(createdStudent);
        assertEquals("encodedPassword", createdStudent.getAccount().getPasswordHash());
        verify(accountRepository, times(1)).save(account);
        verify(studentRepository, times(1)).save(student);
    }
}

