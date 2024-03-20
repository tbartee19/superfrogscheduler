package edu.tcu.cs.superfrogscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// if we define any beans directly we also need -
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperfrogScheduler {
    public static void main(String[] args) {
        SpringApplication.run(SuperfrogScheduler.class, args);
    }
    // bean here if necessary 
    // @Bean
    
}

