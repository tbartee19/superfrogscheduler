package edu.tcu.cs.superfrogscheduler.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(account.getEmail())
                .password(account.getPasswordHash())
                .roles(account.getRole())
                .build();
    }
}
