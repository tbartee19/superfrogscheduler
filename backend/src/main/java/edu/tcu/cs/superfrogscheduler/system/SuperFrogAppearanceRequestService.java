package edu.tcu.cs.superfrogscheduler.system;

import java.time.LocalDate;
import org.springframework.security.access.prepost.PreAuthorize;
import edu.tcu.cs.superfrogscheduler.model.SearchCriteria;
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SuperFrogAppearanceRequest approveRequest(Integer requestId) {
        SuperFrogAppearanceRequest request = findById(requestId);
        if(request.getStatus() == RequestStatus.PENDING && request.getEventDate().isAfter(LocalDate.now().plusDays(5))) {
            request.setStatus(RequestStatus.APPROVED);
            superFrogAppearanceRequestRepository.save(request);
            sendApprovalNotification(request);
            manageConflictingRequests(request);
            System.out.println("Request Approved: " + request.getRequestId());
        } else {
            throw new IllegalStateException("Request cannot be approved: Either not pending or too close to event date.");
        }
        return request;
    }

    private void manageConflictingRequests(SuperFrogAppearanceRequest approvedRequest) {
        List<SuperFrogAppearanceRequest> conflictingRequests = superFrogAppearanceRequestRepository.findByEventDate(approvedRequest.getEventDate());
        for (SuperFrogAppearanceRequest req : conflictingRequests) {
            if (!req.getRequestId().equals(approvedRequest.getRequestId())) {
                req.setStatus(RequestStatus.REJECTED);
                req.setRejectionReason("Date and time not available anymore due to another approved event");
                superFrogAppearanceRequestRepository.save(req);
            }
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SuperFrogAppearanceRequest rejectRequest(Integer requestId, String rejectionReason) {
        SuperFrogAppearanceRequest request = findById(requestId);
        if(request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.REJECTED);
            request.setRejectionReason(rejectionReason);
            superFrogAppearanceRequestRepository.save(request);
            sendRejectionNotification(request);
            System.out.println("Request Rejected: " + request.getRequestId() + " with reason: " + rejectionReason);
        } else {
            throw new IllegalStateException("Request cannot be rejected because it is not in PENDING status: " + requestId);
        }
        return request;
    }


    private void sendApprovalNotification(SuperFrogAppearanceRequest request) {
        // Placeholder for sending approval notification
        System.out.println("Sending approval notification for Request ID: " + request.getRequestId());
        // Actual email/notification sending logic goes here
    }

    private void sendRejectionNotification(SuperFrogAppearanceRequest request) {
        // Placeholder for sending rejection notification
        System.out.println("Sending rejection notification for Request ID: " + request.getRequestId() + ", Reason: " + request.getRejectionReason());
        // Actual email/notification sending logic goes here
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

    public List<SuperFrogAppearanceRequest> search(SearchCriteria criteria) {

        return null;
    }


//    public List<SuperFrogAppearanceRequest> search(SearchCriteria criteria) {
//        return superFrogAppearanceRequestRepository.findAll((Specification<SuperFrogAppearanceRequest>) (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (criteria.getEventTitle() != null) {
//                predicates.add(cb.like(cb.lower(root.get("eventTitle")), "%" + criteria.getEventTitle().toLowerCase() + "%"));
//            }
//            if (criteria.getEventDate() != null) {
//                predicates.add(cb.equal(root.get("eventDateTime"), criteria.getEventDate()));
//            }
//            if (criteria.getContactName() != null) {
//                Predicate firstNamePredicate = cb.like(cb.lower(root.get("contactFirstName")), "%" + criteria.getContactName().toLowerCase() + "%");
//                Predicate lastNamePredicate = cb.like(cb.lower(root.get("contactLastName")), "%" + criteria.getContactName().toLowerCase() + "%");
//                predicates.add(cb.or(firstNamePredicate, lastNamePredicate));
//            }
//            if (criteria.getStatus() != null) {
//                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        });
//    }
}


