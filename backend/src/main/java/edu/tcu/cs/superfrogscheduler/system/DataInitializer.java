package edu.tcu.cs.superfrogscheduler.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createSpiritDirectorAccount();
    }

    private void createSpiritDirectorAccount() {
        String email = "spiritdirector@tcu.edu";
        String password = "superfrog1";
        String role = "ADMIN"; // Ensure this matches the role check in your security configuration

        if (!accountRepository.existsByEmail(email)) {
            Account account = new Account();
            account.setEmail(email);
            account.setPasswordHash(passwordEncoder.encode(password));
            account.setRole(role);
            account.setActive(true);
            accountRepository.save(account);

            System.out.println("Spirit Director account created successfully.");
        } else {
            System.out.println("Spirit Director account already exists.");
        }
    }
}
