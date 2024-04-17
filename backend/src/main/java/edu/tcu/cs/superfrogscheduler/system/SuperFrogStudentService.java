package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.util.Base64;

// service to create a new superfrog student
@Service
public class SuperFrogStudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SuperFrogStudentRepository studentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public SuperFrogStudent createSuperFrogStudent(SuperFrogStudent newStudent) {
        if (accountRepository.existsByEmail(newStudent.getAccount().getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        
        // set role for new account to student so it can be differentiated from spirit directors later
        newStudent.getAccount().setRole("SUPERFROG_STUDENT");
        
        String tempPassword = generateTemporaryPassword();
        newStudent.getAccount().setPasswordHash(passwordEncoder.encode(tempPassword));
    
        // save student to student repository and their account credentials to account
        Account savedAccount = accountRepository.save(newStudent.getAccount());
        newStudent.setAccount(savedAccount);
        SuperFrogStudent savedStudent = studentRepository.save(newStudent);
    
        // temporary logging temp password here for future implementation
        System.out.println("Temporary Password for " + savedStudent.getFirstName() + ": " + tempPassword);

        return savedStudent;
    }

    private String generateTemporaryPassword() {
        // generate a secure random password
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[12];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

