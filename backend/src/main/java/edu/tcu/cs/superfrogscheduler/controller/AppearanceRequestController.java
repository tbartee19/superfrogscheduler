package edu.tcu.cs.superfrogscheduler.controller;

import javax.validation.Valid;

import edu.tcu.cs.superfrogscheduler.system.*;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// AppearanceRequestController
// manages appearance requests
// this could include creating, editing, viewing, and deleting requests
// as well as approval/rejection by the Spirit Director.

// use Cases 1-12, 24, 25, 26 i believe


@RestController
public class AppearanceRequestController {
    // class variables
    private final SuperFrogAppearanceRequestService superFrogAppearanceRequestService;
    private final SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
    private final SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

    @Autowired
    private NotificationService notificationService;

    // constructor
    public AppearanceRequestController(SuperFrogAppearanceRequestService superFrogAppearanceRequestService, SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter, SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter) {
        this.superFrogAppearanceRequestService = superFrogAppearanceRequestService;
        this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
        this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
    }


    // use case 1 - Customer requests a SuperFrog appearance
    @PostMapping("/api/appearances")
    public Result addSuperFrogAppearanceRequest(@Valid @RequestBody SuperFrogAppearanceRequestDto appearanceRequestDto) {
        SuperFrogAppearanceRequest newAppearance = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(appearanceRequestDto);
        newAppearance.setStatus(RequestStatus.PENDING);
        SuperFrogAppearanceRequest savedAppearance = this.superFrogAppearanceRequestService.save(newAppearance);
        SuperFrogAppearanceRequestDto savedAppearanceDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(savedAppearance);
        return new Result(true, HttpStatusCode.SUCCESS, "Add Success", savedAppearanceDto);
    }


    // use case 2 - Customer edits request details
    @PutMapping("/api/appearances/{requestId}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @Valid @RequestBody SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto) {
        SuperFrogAppearanceRequest update = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(superFrogAppearanceRequestDto);
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.update(requestId, update);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Success", updatedRequestDto);
    }

    @PutMapping("/api/appearances/{requestId}/status/{status}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @PathVariable RequestStatus status) {
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.updateStatus(requestId, status);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Status Success", updatedRequestDto);
    }


    // use case 3 - Customer cancels a submitted request
    @DeleteMapping("/api/appearances/{requestId}")
    public Result deleteSuperFrogAppearanceRequest(@PathVariable Integer requestId) {
        this.superFrogAppearanceRequestService.delete(requestId);
        return new Result(true, HttpStatusCode.SUCCESS, "Delete Success");
    }

    // use case 4 - approves an appearance request
    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<Result> approveRequest(@PathVariable Integer id) {
        try {
            SuperFrogAppearanceRequest request = superFrogAppearanceRequestService.findById(id);
            if (!superFrogAppearanceRequestService.areThereConflicts(request)) {
                request.setStatus(RequestStatus.APPROVED);
                superFrogAppearanceRequestService.save(request);
                superFrogAppearanceRequestService.handleCalendarAndConflicts(request);
                notificationService.sendNotification("Approved Request: " + request.getEventTitle());
                return ResponseEntity.ok(new Result(true, HttpStatusCode.SUCCESS, "Request Approved", request));
            } else {
                return ResponseEntity.badRequest().body(new Result(false, HttpStatusCode.CONFLICT, "Conflicting requests found."));
            }
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(false, HttpStatusCode.NOT_FOUND, "Request not found: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(false, HttpStatusCode.INTERNAL_SERVER_ERROR, "Approval Error: " + e.getMessage()));
        }
    }


    // use case 4 - rejects an appearance request
    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<Result> rejectRequest(@PathVariable Integer id, @RequestBody String rejectionReason) {
        try {
            SuperFrogAppearanceRequest request = superFrogAppearanceRequestService.findById(id);
            request.setStatus(RequestStatus.REJECTED);
            request.setRejectionReason(rejectionReason);
            superFrogAppearanceRequestService.save(request);
            notificationService.sendNotification("Rejected Request: " + request.getEventTitle() + ", Reason: " + rejectionReason);
            return ResponseEntity.ok(new Result(true, HttpStatusCode.SUCCESS, "Request Rejected", request));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(false, HttpStatusCode.NOT_FOUND, "Request not found: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(false, HttpStatusCode.INTERNAL_SERVER_ERROR, "Rejection Error: " + e.getMessage()));
        }
    }


    @GetMapping("/api/appearances/{requestId}")
    public Result findSuperFrogAppearanceById(@PathVariable int requestId) {
        SuperFrogAppearanceRequest foundAppearance = this.superFrogAppearanceRequestService.findById(requestId);
        SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(foundAppearance);
        return new Result(true, HttpStatusCode.SUCCESS, "Find One Success", superFrogAppearanceRequestDto);
    }

    @GetMapping("/api/appearances")
    public Result findAllSuperFrogAppearances() {
        List<SuperFrogAppearanceRequest> foundAppearance = this.superFrogAppearanceRequestService.findAll();

        List<SuperFrogAppearanceRequestDto> appearanceRequestDtos = foundAppearance.stream()
                .map(this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, HttpStatusCode.SUCCESS, "Find All Success", appearanceRequestDtos);
    }

    @PreAuthorize("hasRole('SPIRIT_DIRECTOR')")
    @PostMapping("/requestSuperFrog")
    public ResponseEntity<?> requestSuperFrogAppearance(@RequestBody SuperFrogAppearanceRequestDto requestDto) {

        try {
            SuperFrogAppearanceRequest newRequest = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(requestDto);
            newRequest.setStatus(RequestStatus.PENDING); // Initial status as per use case
            SuperFrogAppearanceRequest savedRequest = superFrogAppearanceRequestService.createRequest(newRequest);
            return ResponseEntity.ok(superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(savedRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in requesting SuperFrog appearance: " + e.getMessage());
        }
    }

    @Autowired
    private SuperFrogAppearanceRequestService service;


    @GetMapping("/search")
    public ResponseEntity<List<SuperFrogAppearanceRequest>> searchRequests(@RequestParam Map<String, Object> criteria) {
        List<SuperFrogAppearanceRequest> results = service.searchRequests(criteria);
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperFrogAppearanceRequest> getRequestById(@PathVariable Integer id) {
        try {
            SuperFrogAppearanceRequest request = service.findRequestById(id);
            return ResponseEntity.ok(request);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/signup/{requestId}/{studentId}")
    public ResponseEntity<String> signUpForAppearance(@PathVariable Integer requestId, @PathVariable Integer studentId) {
        try {
            superFrogAppearanceRequestService.SuperFrogStudentSignup(requestId, String.valueOf(studentId));
            return ResponseEntity.ok("Signed up successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel-signup/{requestId}/{studentId}")
    public ResponseEntity<String> cancelSignUp(@PathVariable Integer requestId, @PathVariable Integer studentId) {
        try {
            superFrogAppearanceRequestService.SuperFrogStudentCancellation(requestId, String.valueOf(studentId));
            return ResponseEntity.ok("Signup cancelled successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}


