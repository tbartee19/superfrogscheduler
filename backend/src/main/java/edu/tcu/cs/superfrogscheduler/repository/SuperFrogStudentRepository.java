package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

// repository to store superfrog students in database
public interface SuperFrogStudentRepository extends MongoRepository<SuperFrogStudent, String> {
    
    List<SuperFrogStudent> findByFirstNameAndLastNameAndPhoneNumberAndEmail(String firstName, String lastName, String phoneNumber, String email);
    List<SuperFrogStudent> findByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);
    List<SuperFrogStudent> findByFirstNameAndLastName(String firstName, String lastName);
    List<SuperFrogStudent> findByFirstNameAndPhoneNumber(String firstName, String phoneNumber);
    List<SuperFrogStudent> findByFirstNameAndEmail(String firstName, String email);
    List<SuperFrogStudent> findByLastNameAndPhoneNumber(String lastName, String phoneNumber);
    List<SuperFrogStudent> findByLastNameAndEmail(String lastName, String email);
    List<SuperFrogStudent> findByPhoneNumberAndEmail(String phoneNumber, String email);
    List<SuperFrogStudent> findByFirstName(String firstName);
    List<SuperFrogStudent> findByLastName(String lastName);
    List<SuperFrogStudent> findByPhoneNumber(String phoneNumber);
    Optional<SuperFrogStudent> findByEmail(String email);
    Optional<SuperFrogStudent> findById(String id);
    class SuperFrogAppearanceRequestRepository {

    }
}
