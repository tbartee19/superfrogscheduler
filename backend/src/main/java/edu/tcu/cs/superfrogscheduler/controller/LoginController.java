package edu.tcu.cs.superfrogscheduler.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.model.LoginRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired 
    private SuperFrogStudentRepository studentRepository;

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Account account = accountRepository.findByEmail(loginRequest.getUsername()).orElseThrow(
                () -> new Exception("User not found")
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User authenticated successfully");
            if ("STUDENT".equals(account.getRole())) {
                Optional<SuperFrogStudent> studentOpt = studentRepository.findByEmail(loginRequest.getUsername());
                if (studentOpt.isPresent()) {
                    response.put("student", studentOpt.get());
                } else {
                    // Consider what to do if no student details are found but the role is student
                    throw new Exception("Student details not found for the authenticated user");
                }
            }

            // Determine the role and customize response based on the role
            
            
            response.put("role", account.getRole()); // Send role back to the client
           
            

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Invalid username or password: " + ex.getMessage());
        }
    }

}

