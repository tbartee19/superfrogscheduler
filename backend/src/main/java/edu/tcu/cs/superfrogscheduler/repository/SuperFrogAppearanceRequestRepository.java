package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperFrogAppearanceRequestRepository extends JpaRepository<SuperFrogAppearanceRequest, Integer> {
    List<SuperFrogAppearanceRequest> findByRequestIdIn(List<Integer> appearanceRequestIdList);
    List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status);
    // List<SuperFrogAppearanceRequest> findByStatusAndStudent(RequestStatus status, SuperFrogStudent student);
}
