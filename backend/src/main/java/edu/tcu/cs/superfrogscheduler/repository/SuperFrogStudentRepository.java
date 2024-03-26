package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// repository to store superfrog students in database
public interface SuperFrogStudentRepository extends MongoRepository<SuperFrogStudent, String> {
    
    // example to list students by last name
    List<SuperFrogStudent> findByLastName(String lastName);
}
