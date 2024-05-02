package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;

import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;

import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;

import java.time.LocalTime;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SuperFrogAppearanceRequestService {
    private final SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;
    private SuperFrogStudentRepository superfrogStudentRepository;
    private NotificationService notificationService;

    @Autowired
    public SuperFrogAppearanceRequestService(
            SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository,
            SuperFrogStudentRepository superfrogStudentRepository,
            NotificationService notificationService) {
        this.superFrogAppearanceRequestRepository = superFrogAppearanceRequestRepository;
        this.superfrogStudentRepository = superfrogStudentRepository;
        this.notificationService = notificationService;
    }



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
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    validateUpdateDetails(update);

                    applyUpdatedDetails(oldRequest, update);

                    SuperFrogAppearanceRequest savedRequest = this.superFrogAppearanceRequestRepository.save(oldRequest);

                    notifyStakeholders(savedRequest);

                    return savedRequest;
                })
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }

    private void applyUpdatedDetails(SuperFrogAppearanceRequest oldRequest, SuperFrogAppearanceRequest update) {
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
        // Ensure the status is set correctly based on your business rules
        oldRequest.setStatus(update.getStatus());
    }

    private void validateUpdateDetails(SuperFrogAppearanceRequest details) {
        if (details.getContactFirstName() == null || details.getContactFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (details.getContactLastName() == null || details.getContactLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (details.getPhoneNumber() == null || !details.getPhoneNumber().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")) {
            throw new IllegalArgumentException("Phone number must be in the format (999) 999-9999.");
        }
        if (details.getEmail() == null || !details.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (details.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is required.");
        }
        if (details.getStartTime() == null || details.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time are required.");
        }
        if (details.getEventType() == null) {
            throw new IllegalArgumentException("Type of the event is required.");
        }
        if (details.getEventTitle() == null || details.getEventTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Event title is required.");
        }
        if (details.getNameOfOrg() == null || details.getNameOfOrg().trim().isEmpty()) {
            throw new IllegalArgumentException("Name of the organization is required.");
        }
        if (details.getAddress() == null || details.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Event address is required.");
        }
        if (details.getSpecialInstructions() == null) {
            details.setSpecialInstructions(""); // Set default empty if null
        }
        if (details.getExpenses() == null) {
            details.setExpenses(""); // Set default empty if null
        }
        if (details.getOutsideOrgs() == null) {
            details.setOutsideOrgs(""); // Set default empty if null
        }
        if (details.getDescription() == null || details.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Detailed event description is required.");
        }
    }


    private void notifyStakeholders(SuperFrogAppearanceRequest request) {
        notificationService.sendNotification("The appearance request has been updated: " + request.getEventTitle());
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

    public SuperFrogAppearanceRequest reverseDecision(Integer requestId){
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    RequestStatus currentStatus = oldRequest.getStatus();
                    if(currentStatus == RequestStatus.APPROVED) oldRequest.setStatus(RequestStatus.REJECTED);
                    else if(currentStatus == RequestStatus.REJECTED) oldRequest.setStatus(RequestStatus.APPROVED);
                    return this.superFrogAppearanceRequestRepository.save(oldRequest);
                })
                .orElseThrow(()-> new ObjectNotFoundException("superfrogappearncerequest", requestId));
    }

    public SuperFrogAppearanceRequest setIncomplete(Integer requestId){
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    if(oldRequest.getEndTime().isBefore(LocalTime.now())){
                        oldRequest.setStatus(RequestStatus.INCOMPLETE);
                    }
                    else{
                        //trigger warning
                    }
                    return this.superFrogAppearanceRequestRepository.save(oldRequest);
                }).orElseThrow(()-> new ObjectNotFoundException("superfrogappearancerequest", requestId));
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


    public List<SuperFrogAppearanceRequest> searchRequests(Map<String, Object> criteria) {
        return superFrogAppearanceRequestRepository.findByCriteria(criteria);
    }

    public SuperFrogAppearanceRequest findRequestById(Integer requestId) {
        return superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }

    // Spirit Director assigns student
    public void assignSuperFrogStudent(Integer requestId, String studentId) {
        // Fetch the request using the provided request ID
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request not found.", requestId));

        // Check the existence of the student using the provided student ID
        if (superfrogStudentRepository.findById(studentId).isEmpty()) {
            throw new ObjectNotFoundException("Student not found", studentId);
        }

        // Ensure the request is approved before assigning a student
        if (request.getStatus() != RequestStatus.APPROVED) {
            throw new ObjectNotFoundException("Cannot assign a student to an unapproved request.", requestId.toString());
        }

        // Update the request status to ASSIGNED and set the student ID
        request.setStatus(RequestStatus.ASSIGNED);
        request.setStudentId(studentId);  // Make sure you have this setter or similar in your SuperFrogAppearanceRequest class
        superFrogAppearanceRequestRepository.save(request);

        // Send notifications
        notificationService.sendNotification("The appearance request has been assigned.");
        notificationService.sendNotification("You have been assigned to an appearance request on: " + request.getEventDate());
    }


    public void SuperFrogStudentSignup(Integer requestId, String studentId) {
        // Fetch the request using the provided request ID
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request not found.", requestId));

        // Check the existence of the student using the provided student ID
        if (superfrogStudentRepository.findById(studentId).isEmpty()) {
            throw new ObjectNotFoundException("Student not found.", studentId);
        }

        // Ensure the request is approved before allowing signup
        if (!request.getStatus().equals(RequestStatus.APPROVED)) {
            throw new ObjectNotFoundException("Request is not available for signup", requestId);
        }

        // Update the request status to ASSIGNED
        request.setStatus(RequestStatus.ASSIGNED);
        superFrogAppearanceRequestRepository.save(request);

        // Notify about the signup
        notificationService.sendNotification("A student has signed up for the appearance request on: " + request.getEventDate());
    }


    // Student cancels an appearance request sign-up
    public void SuperFrogStudentCancellation(Integer requestId, String studentId) {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request not found", requestId));

        if (request.getStatus() != RequestStatus.ASSIGNED) {
            throw new ObjectNotFoundException("Request is not currently assigned", requestId);
        }

        // Assuming `studentId` is a string and comparing directly if the stored ID matches
        if (!request.getStudentId().equals(studentId)) {
            throw new ObjectNotFoundException("This student is not signed up for this request", studentId);
        }

        if (LocalDate.now().plusDays(2).isAfter(request.getEventDate())) {
            throw new ObjectNotFoundException("It is too late to cancel this appearance", requestId);
        }

        // Clear the student ID and set the request back to APPROVED
        request.setStudentId(null); // Assuming there's a method to remove student ID
        request.setStatus(RequestStatus.APPROVED);
        superFrogAppearanceRequestRepository.save(request);

        // Send notifications about the cancellation
        notificationService.sendNotification("An appearance request has been cancelled and is now available for sign up again.");
    }




}






