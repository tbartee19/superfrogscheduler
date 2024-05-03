package edu.tcu.cs.superfrogscheduler.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class SearchCriteria {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String firstName;
    private String lastName;
    private String status;
    private String assignedSuperFrog;

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedSuperFrog() {
        return assignedSuperFrog;
    }

    public void setAssignedSuperFrog(String assignedSuperFrog) {
        this.assignedSuperFrog = assignedSuperFrog;
    }


        public Specification<SuperFrogAppearanceRequest> toSpecification() {
            return (root, query, cb) -> {
                Predicate p = cb.conjunction();

                if (startDate != null && endDate != null) {
                    p = cb.and(p, cb.between(root.get("eventDate"), startDate, endDate));
                } else if (startDate != null) {
                    p = cb.and(p, cb.greaterThanOrEqualTo(root.get("eventDate"), startDate));
                } else if (endDate != null) {
                    p = cb.and(p, cb.lessThanOrEqualTo(root.get("eventDate"), endDate));
                }

                if (title != null) {
                    p = cb.and(p, cb.like(root.get("eventTitle"), "%" + title + "%"));
                }

                if (firstName != null) {
                    p = cb.and(p, cb.like(root.get("contactFirstName"), "%" + firstName + "%"));
                }

                if (lastName != null) {
                    p = cb.and(p, cb.like(root.get("contactLastName"), "%" + lastName + "%"));
                }

                if (status != null) {
                    p = cb.and(p, cb.equal(root.get("status"), status));
                }

                if (assignedSuperFrog != null) {
                    p = cb.and(p, cb.equal(root.get("assignedSuperFrog"), assignedSuperFrog));
                }

                return p;
            };
        }
    }


