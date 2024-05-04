package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SuperFrogAppearanceRequestRepository extends JpaRepository<SuperFrogAppearanceRequest, Integer>, JpaSpecificationExecutor<SuperFrogAppearanceRequest> {
    List<SuperFrogAppearanceRequest> findByRequestIdIn(List<Integer> appearanceRequestIdList);

    List<SuperFrogAppearanceRequest> findByStatus(RequestStatus status);
    // List<SuperFrogAppearanceRequest> findByStatusAndStudent(RequestStatus status,
    // SuperFrogStudent student);

    @Query("select r from SuperFrogAppearanceRequest r where r.eventDate = :date and r.startTime <= :endTime and r.endTime >= :startTime")
    List<SuperFrogAppearanceRequest> findByEventDateAndStartTimeAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);



}
