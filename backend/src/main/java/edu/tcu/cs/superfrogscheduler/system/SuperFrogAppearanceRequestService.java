package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    }


