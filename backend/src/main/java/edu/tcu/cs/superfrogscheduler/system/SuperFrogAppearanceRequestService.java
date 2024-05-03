package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SuperFrogAppearanceRequestService {
    private final SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;



    public SuperFrogAppearanceRequestService(
            SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository) {
        this.superFrogAppearanceRequestRepository = superFrogAppearanceRequestRepository;
    }


    public SuperFrogAppearanceRequest findById(Integer requestId) {
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }

    public SuperFrogAppearanceRequest approveRequest(Integer requestId) {
        SuperFrogAppearanceRequest request = findById(requestId);
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.APPROVED);
            // Add to calendar logic here if applicable
            return superFrogAppearanceRequestRepository.save(request);
        } else {
            throw new IllegalStateException("Request cannot be approved");
        }
    }

    public SuperFrogAppearanceRequest rejectRequest(Integer requestId, String rejectionReason) {
        SuperFrogAppearanceRequest request = findById(requestId);
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.REJECTED);
            request.setRejectionReason(rejectionReason);
            return superFrogAppearanceRequestRepository.save(request);
        } else {
            throw new IllegalStateException("Request cannot be rejected");
        }
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

    // Method to handle TCU event requests specifically
    public SuperFrogAppearanceRequest EventRequest(SuperFrogAppearanceRequest request) {
        // You might need to implement a check to ensure there's no overlapping TCU event
        // For now, it directly assigns/approves the event
        request.setStatus(RequestStatus.ASSIGNED);  // or APPROVED, based on internal decision logic
        return superFrogAppearanceRequestRepository.save(request);
    }

    public List<SuperFrogAppearanceRequest> searchAppearanceRequests(LocalDate startDate, LocalDate endDate, String title, String firstName, String lastName, String status, String assignedSuperFrog) {
        return superFrogAppearanceRequestRepository.findAll().stream()
                .filter(request -> (startDate == null || endDate == null || (request.getEventDate().isEqual(startDate) || request.getEventDate().isEqual(endDate) || (request.getEventDate().isAfter(startDate) && request.getEventDate().isBefore(endDate))))
                        && (title == null || request.getEventTitle().equalsIgnoreCase(title))
                        && (firstName == null || request.getContactFirstName().equalsIgnoreCase(firstName))
                        && (lastName == null || request.getContactLastName().equalsIgnoreCase(lastName))
                        && (status == null || request.getStatus().toString().equalsIgnoreCase(status))
                        && (assignedSuperFrog == null || request.getAssignedSuperFrog().equalsIgnoreCase(assignedSuperFrog)))
                .collect(Collectors.toList());
    }

    // In SuperFrogAppearanceRequestService
    public SuperFrogAppearanceRequest editAppearanceRequest(Integer requestId, SuperFrogAppearanceRequestDto requestDto) {
        return superFrogAppearanceRequestRepository.findById(requestId)
                .map(existingRequest -> {
                    existingRequest.setEventDate(requestDto.eventDate());
                    existingRequest.setStartTime(requestDto.startTime());
                    existingRequest.setEndTime(requestDto.endTime());
                    existingRequest.setContactFirstName(requestDto.contactFirstName());
                    existingRequest.setContactLastName(requestDto.contactLastName());
                    existingRequest.setPhoneNumber(requestDto.phoneNumber());
                    existingRequest.setEmail(requestDto.email());
                    existingRequest.setEventType(String.valueOf(EventType.valueOf(requestDto.eventType())));
                    existingRequest.setEventTitle(requestDto.eventTitle());
                    existingRequest.setNameOfOrg(requestDto.nameOfOrg());
                    existingRequest.setAddress(requestDto.address());
                    existingRequest.setSpecialInstructions(requestDto.specialInstructions());
                    existingRequest.setExpenses(requestDto.expenses());
                    existingRequest.setOutsideOrgs(requestDto.outsideOrgs());
                    existingRequest.setDescription(requestDto.description());
                    return superFrogAppearanceRequestRepository.save(existingRequest);
                })
                .orElseThrow(() -> new ObjectNotFoundException("SuperFrogAppearanceRequest", requestId));
    }



}