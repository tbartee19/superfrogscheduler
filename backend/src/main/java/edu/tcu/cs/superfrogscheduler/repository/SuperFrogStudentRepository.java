package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// repository to store superfrog students in database
public interface SuperFrogStudentRepository extends MongoRepository<SuperFrogStudent, String> {
    
    // example to list students by last name
    List<SuperFrogStudent> findByLastName(String lastName);

    class SuperFrogAppearanceRequestRepository {

        public List<SuperFrogAppearanceRequest> findAll() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        }

        public Object findById(Integer requestId) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findById'");
        }

        public List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findByStatus'");
        }

        public List<SuperFrogAppearanceRequest> findByStatusAndStudent(RequestStatus status, SuperFrogStudent student) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'findByStatusAndStudent'");
        }

        public void deleteById(Integer requestId) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
        }

        public SuperFrogAppearanceRequest save(SuperFrogAppearanceRequest newSuperFrogAppearanceRequest) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
        }
    }
}
