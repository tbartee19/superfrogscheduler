package edu.tcu.cs.superfrogscheduler.controller;

import java.util.HashMap;
import java.util.Map;

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
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository accountRepository;

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
            response.put("role", account.getRole()); 
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}

