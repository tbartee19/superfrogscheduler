package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SuperFrogAppearanceRequestRepository extends JpaRepository<SuperFrogAppearanceRequest, Integer>, JpaSpecificationExecutor<SuperFrogAppearanceRequest> {
    List<SuperFrogAppearanceRequest> findByRequestIdIn(List<Integer> appearanceRequestIdList);

    List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status);
    // List<SuperFrogAppearanceRequest> findByStatusAndStudent(RequestStatus status,
    // SuperFrogStudent student);

    List<SuperFrogAppearanceRequest> findByEventDateAndEventTitle(LocalDate eventDate, String eventTitle);


}