package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SearchCriteria;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
        SuperFrogAppearanceRequest request = findById(requestId);  // Reuse the findById to handle not found exception
        request.setStatus(RequestStatus.APPROVED);
        return superFrogAppearanceRequestRepository.save(request);
    }

    public SuperFrogAppearanceRequest rejectRequest(Integer requestId, String rejectionReason) {
        SuperFrogAppearanceRequest request = findById(requestId);  // Reuse the findById to handle not found exception
        request.setStatus(RequestStatus.REJECTED);
        request.setReason(rejectionReason);
        return superFrogAppearanceRequestRepository.save(request);
    }

    public List<SuperFrogAppearanceRequest> findAll() {
        return this.superFrogAppearanceRequestRepository.findAll();
    }

    public List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status) {
        return this.superFrogAppearanceRequestRepository.findByStatus(status);
    }

    // public List<SuperFrogAppearanceRequest> findByStatusAndStudent(RequestStatus
    // status, SuperFrogStudent student){
    // return
    // this.superFrogAppearanceRequestRepository.findByStatusAndStudent(status,
    // student);
    // }

    public SuperFrogAppearanceRequest save(SuperFrogAppearanceRequest newSuperFrogAppearanceRequest) {
        return this.superFrogAppearanceRequestRepository.save(newSuperFrogAppearanceRequest);
    }

    public SuperFrogAppearanceRequest update(Integer requestId, SuperFrogAppearanceRequest update) {
        return this.superFrogAppearanceRequestRepository.findById(requestId)
                .map(oldRequest -> {
                    oldRequest.setContactFirstName(update.getContactFirstName());
                    oldRequest.setContactLastName(update.getContactLastName());
                    oldRequest.setPhoneNumber(update.getPhoneNumber());
                    oldRequest.setEmail(update.getEmail());
                    oldRequest.setEventType(update.getEventType().toString());
                    oldRequest.setEventTitle(update.getEventTitle());
                    oldRequest.setNameOfOrg(update.getNameOfOrg());
                    oldRequest.setAddress(update.getAddress());
                    oldRequest.setIsOnTCUCampus(update.getIsOnTCUCampus());
                    oldRequest.setSpecialInstructions(update.getSpecialInstructions());
                    oldRequest.setExpenses(update.getExpenses());
                    oldRequest.setOutsideOrgs(update.getOutsideOrgs());
                    oldRequest.setDescription(update.getDescription());
                    oldRequest.setStatus(update.getStatus()); // TODO status isnt updated
                    return this.superFrogAppearanceRequestRepository.save(oldRequest);
                })
                .orElseThrow(() -> new ObjectNotFoundException("superfrogappearancerequest", requestId));
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


    public SuperFrogAppearanceRequest createRequestBySpiritDirector(SuperFrogAppearanceRequest request) {

        return request;
    }

    public List<SuperFrogAppearanceRequest> search(SearchCriteria criteria) {
        return superFrogAppearanceRequestRepository.findAll((Specification<SuperFrogAppearanceRequest>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getEventTitle() != null) {
                predicates.add(cb.like(cb.lower(root.get("eventTitle")), "%" + criteria.getEventTitle().toLowerCase() + "%"));
            }
            if (criteria.getEventDate() != null) {
                predicates.add(cb.equal(root.get("eventDateTime"), criteria.getEventDate()));
            }
            if (criteria.getContactName() != null) {
                Predicate firstNamePredicate = cb.like(cb.lower(root.get("contactFirstName")), "%" + criteria.getContactName().toLowerCase() + "%");
                Predicate lastNamePredicate = cb.like(cb.lower(root.get("contactLastName")), "%" + criteria.getContactName().toLowerCase() + "%");
                predicates.add(cb.or(firstNamePredicate, lastNamePredicate));
            }
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

//    public Specification<SuperFrogAppearanceRequest> createSpecification(SearchCriteria criteria) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (criteria.getEventTitle() != null) {
//                predicates.add(cb.like(root.get("eventTitle"), "%" + criteria.getEventTitle() + "%"));
//            }
//            if (criteria.getEventDate() != null) {
//                predicates.add(cb.equal(root.get("eventDate"), criteria.getEventDate()));
//            }
//            if (criteria.getContactName() != null) {
//                predicates.add(cb.like(root.get("contactName"), "%" + criteria.getContactName() + "%"));
//            }
//            if (criteria.getStatus() != null) {
//                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
//            }
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//
//    }



}

