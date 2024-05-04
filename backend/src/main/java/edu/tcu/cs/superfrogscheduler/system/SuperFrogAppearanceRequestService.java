package edu.tcu.cs.superfrogscheduler.system;

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

    public SuperFrogAppearanceRequest approveRequest(Integer requestId) throws Exception {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request", requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request must be in 'PENDING' status.");
        }

        // Check for competing requests
        handleCompetingRequests(requestId, request.getEventDate(), request.getStartTime(), request.getEndTime());

        request.setStatus(RequestStatus.APPROVED);
        return superFrogAppearanceRequestRepository.save(request);
    }

    private void handleCompetingRequests(Integer requestId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<SuperFrogAppearanceRequest> competingRequests = superFrogAppearanceRequestRepository.findByEventDateAndStartTimeAndEndTime(date, startTime, endTime);
        for (SuperFrogAppearanceRequest compRequest : competingRequests) {
            if (!compRequest.getRequestId().equals(requestId)) {
                compRequest.setStatus(RequestStatus.REJECTED);
                compRequest.setReason("Time slot no longer available due to another approval.");
                superFrogAppearanceRequestRepository.save(compRequest);
            }
        }
    }


    public SuperFrogAppearanceRequest rejectRequest(Integer requestId, String reason) throws Exception {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request", requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request must be in 'PENDING' status.");
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setReason(reason);
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

    public SuperFrogAppearanceRequest setComplete(Integer requestId){
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(doneRequest -> {
                    RequestStatus currentStatus = doneRequest.getStatus();
                    if((currentStatus == RequestStatus.APPROVED) && (doneRequest.getEndTime().isBefore(LocalTime.now()))) doneRequest.setStatus(RequestStatus.COMPLETED);
                    else{
                        //trigger warning
                    }
                    return this.superFrogAppearanceRequestRepository.save(doneRequest);
                }).orElseThrow(()-> new ObjectNotFoundException("superfrograppearancerequest", requestId));
    }


    public SuperFrogAppearanceRequest cancelRequest(Integer requestId){
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(cancel -> {
                    RequestStatus currentStatus = cancel.getStatus();
                    if((currentStatus == RequestStatus.ASSIGNED || currentStatus == RequestStatus.APPROVED)){
                        cancel.setStatus(RequestStatus.CANCELLED);
                        cancel.setReason("Cancelled by Spirit Director");
                    }
                    else{
                        //trigger warning
                    }
                    return this.superFrogAppearanceRequestRepository.save(cancel);
                }).orElseThrow(()-> new ObjectNotFoundException("superfrogappearancerequest", requestId));
    }


    }


