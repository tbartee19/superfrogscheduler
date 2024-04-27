package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SuperFrogAppearanceRequestService {
    private final SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

    @Autowired
    private NotificationService notificationService;

    public SuperFrogAppearanceRequestService(
            SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository) {
        this.superFrogAppearanceRequestRepository = superFrogAppearanceRequestRepository;
    }


    public SuperFrogAppearanceRequest findById(Integer requestId) {
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }

    public SuperFrogAppearanceRequest approveRequest(Integer requestId) {
        SuperFrogAppearanceRequest request = findById(requestId);  // Reuse the findById to handle not found exception
        request.setStatus(RequestStatus.APPROVED);
        return superFrogAppearanceRequestRepository.save(request);
    }

    public SuperFrogAppearanceRequest rejectRequest(Integer requestId, String rejectionReason) {
        SuperFrogAppearanceRequest request = findById(requestId);  // Reuse the findById to handle not found exception
        request.setStatus(RequestStatus.REJECTED);
        request.setRejectionReason(rejectionReason);
        return superFrogAppearanceRequestRepository.save(request);
    }

    public List<SuperFrogAppearanceRequest> findAll() {
        return this.superFrogAppearanceRequestRepository.findAll();
    }

    public List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status) {
        return this.superFrogAppearanceRequestRepository.findByStatus(status);
    }

    public SuperFrogAppearanceRequest save(SuperFrogAppearanceRequest newSuperFrogAppearanceRequest) {
        return this.superFrogAppearanceRequestRepository.save(newSuperFrogAppearanceRequest);
    }

    public SuperFrogAppearanceRequest update(Integer requestId, SuperFrogAppearanceRequest update){
        update.setStatus(RequestStatus.PENDING);
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    oldRequest.setEventDate(update.getEventDate());
                    oldRequest.setStartTime(update.getStartTime());
                    oldRequest.setEndTime(update.getEndTime());
                    oldRequest.setContactFirstName(update.getContactFirstName());
                    oldRequest.setContactLastName(update.getContactLastName());
                    oldRequest.setPhoneNumber(update.getPhoneNumber());
                    oldRequest.setEmail(update.getEmail());
                    oldRequest.setEventType(update.getEventType().toString());
                    oldRequest.setEventTitle(update.getEventTitle());
                    oldRequest.setNameOfOrg(update.getNameOfOrg());
                    oldRequest.setAddress(update.getAddress());
                    oldRequest.setSpecialInstructions(update.getSpecialInstructions());
                    oldRequest.setExpenses(update.getExpenses());
                    oldRequest.setOutsideOrgs(update.getOutsideOrgs());
                    oldRequest.setDescription(update.getDescription());
                    oldRequest.setStatus(update.getStatus()); // TODO status isnt updated
                    return this.superFrogAppearanceRequestRepository.save(oldRequest);
                })
                .orElseThrow(()-> new ObjectNotFoundException("superfrogappearancerequest", requestId));
    }

    public void delete(Integer requestId) {
        this.superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("superfrogappearancerequest", requestId));
        this.superFrogAppearanceRequestRepository.deleteById(requestId);
    }

    public SuperFrogAppearanceRequest updateStatus(Integer requestId, RequestStatus status) {
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    oldRequest.setStatus(status);
                    return this.superFrogAppearanceRequestRepository.save(oldRequest);
                })
                .orElseThrow(() -> new ObjectNotFoundException("superfrogappearancerequest", requestId));
    }

    // Check for conflicting requests
    public boolean areThereConflicts(SuperFrogAppearanceRequest request) {
        List<SuperFrogAppearanceRequest> allRequests = findByStatus(RequestStatus.PENDING);
        return allRequests.stream().anyMatch(other -> other.getEventDate().isEqual(request.getEventDate()) &&
                !other.getRequestId().equals(request.getRequestId()) &&
                other.getStartTime().equals(request.getStartTime()));
    }

    // Update the calendar and handle conflicts
    public void handleCalendarAndConflicts(SuperFrogAppearanceRequest request) {
        List<SuperFrogAppearanceRequest> allRequests = findByStatus(RequestStatus.PENDING);
        allRequests.stream()
                .filter(other -> other.getEventDate().isEqual(request.getEventDate()) &&
                        !other.getRequestId().equals(request.getRequestId()) &&
                        other.getStartTime().equals(request.getStartTime()))
                .forEach(conflict -> {
                    conflict.setStatus(RequestStatus.REJECTED);
                    conflict.setRejectionReason("Date and time not available anymore due to another event approval.");
                    superFrogAppearanceRequestRepository.save(conflict);
                });
    }
    // SuperFrogAppearanceRequestService.java

    public SuperFrogAppearanceRequest createRequest(SuperFrogAppearanceRequest request) {
        SuperFrogAppearanceRequest savedRequest = superFrogAppearanceRequestRepository.save(request);
        notificationService.sendNotification("New SuperFrog appearance request created for event: " + request.getEventTitle());
        return savedRequest;
    }


    @Autowired
    private SuperFrogStudentRepository superfrogStudentRepository;

    public void assignSuperFrogStudent(Integer requestId, Integer studentId) {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        SuperFrogStudent student = superfrogStudentRepository.findById(studentId.toString())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        request.setSuperfrogStudent(student);
        request.setStatus(RequestStatus.APPROVED); // Assuming RequestStatus is an enum
        superFrogAppearanceRequestRepository.save(request);
    }

    public List<SuperFrogAppearanceRequest> searchRequests(Map<String, Object> criteria) {
        return superFrogAppearanceRequestRepository.findByCriteria(criteria);
    }

    public SuperFrogAppearanceRequest findRequestById(Integer requestId) {
        return superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }

}


