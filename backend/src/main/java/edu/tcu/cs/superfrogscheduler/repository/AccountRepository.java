package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

// repository to store accounts for students or spirit director
public interface AccountRepository extends MongoRepository<Account, String> {
    // example to find an account by email
    Optional<Account> findByEmail(String email);

    // check if email exists in the database
    boolean existsByEmail(String email);
}