package edu.tcu.cs.superfrogscheduler.controller;

import javax.validation.Valid;

import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

import java.util.List;
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

    // constructor
    public AppearanceRequestController(SuperFrogAppearanceRequestService superFrogAppearanceRequestService, SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter, SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter) {
        this.superFrogAppearanceRequestService = superFrogAppearanceRequestService;
        this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
        this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
    }


    // use case 1 - Customer requests a SuperFrog appearance
    @PostMapping("/api/appearances")
    public Result addSuperFrogAppearanceRequest(@Valid @RequestBody SuperFrogAppearanceRequestDto appearanceRequestDto){
        SuperFrogAppearanceRequest newAppearance = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(appearanceRequestDto);
        newAppearance.setStatus(RequestStatus.PENDING);
        SuperFrogAppearanceRequest savedAppearance = this.superFrogAppearanceRequestService.save(newAppearance);
        SuperFrogAppearanceRequestDto savedAppearanceDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(savedAppearance);
        return new Result(true, HttpStatusCode.SUCCESS, "Add Success", savedAppearanceDto);
    }


    // use case 2 - Customer edits request details
    @PutMapping("/api/appearances/{requestId}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @Valid @RequestBody SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto){
        SuperFrogAppearanceRequest update = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(superFrogAppearanceRequestDto);
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.update(requestId, update);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Success", updatedRequestDto);
    }

    @PutMapping("/api/appearances/{requestId}/status/{status}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @PathVariable RequestStatus status){
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.updateStatus(requestId, status);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Status Success", updatedRequestDto);
    }

    // use case 25 - spirit director reverses an approval/rejection decision
    @PutMapping("/api/appearance/{requestId}")
    public Result reverseAppearanceDecision(@PathVariable Integer requestId){
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.reverseDecision(requestId);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Status reverse success", updatedRequestDto);
    }

    // use case 26 - the spirit director marks an appearance as incomplete
    @PutMapping("/api/appearance/{requestId}")
    public Result markIncomplete(@PathVariable Integer requestId){
        SuperFrogAppearanceRequest incompleteRequest = this.superFrogAppearanceRequestService.setIncomplete(requestId);
        SuperFrogAppearanceRequestDto requestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(incompleteRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Appearance set as incomplete", requestDto);
    }

    // use case 3 - Customer cancels a submitted request
    @DeleteMapping("/api/appearances/{requestId}")
    public Result deleteSuperFrogAppearanceRequest(@PathVariable Integer requestId){
        this.superFrogAppearanceRequestService.delete(requestId);
        return new Result(true, HttpStatusCode.SUCCESS, "Delete Success");
    }



    // other methods
    @GetMapping("/api/appearances/{requestId}")
    public Result findSuperFrogAppearanceById(@PathVariable int requestId) {
        try {
            SuperFrogAppearanceRequest foundAppearance = this.superFrogAppearanceRequestService.findById(requestId);
            SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(foundAppearance);
            return new Result(true, HttpStatusCode.SUCCESS, "Find One Success", superFrogAppearanceRequestDto);
        } catch (ObjectNotFoundException e) {
            return new Result(false, HttpStatusCode.NOT_FOUND, "Request Not Found");
        }
    }


    @GetMapping("/api/appearances")
    public Result findAllSuperFrogAppearances(){
        List<SuperFrogAppearanceRequest> foundAppearance = this.superFrogAppearanceRequestService.findAll();

        List<SuperFrogAppearanceRequestDto> appearanceRequestDtos = foundAppearance.stream()
                .map(this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, HttpStatusCode.SUCCESS, "Find All Success", appearanceRequestDtos);
    }

    // Endpoint to approve an appearance request
    @PutMapping("/api/appearances/{requestId}/approve")
    public ResponseEntity<Result> approveRequest(@PathVariable Integer requestId) {
        SuperFrogAppearanceRequest approvedRequest = superFrogAppearanceRequestService.approveRequest(requestId);
        SuperFrogAppearanceRequestDto dto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(approvedRequest);
        return new ResponseEntity<>(new Result(true, HttpStatusCode.SUCCESS, "Request Approved", dto), HttpStatus.OK);
    }

    // Endpoint to reject an appearance request
    @PutMapping("/api/appearances/{requestId}/reject")
    public ResponseEntity<Result> rejectRequest(@PathVariable Integer requestId, @RequestBody String rejectionReason) {
        SuperFrogAppearanceRequest rejectedRequest = superFrogAppearanceRequestService.rejectRequest(requestId, rejectionReason);
        SuperFrogAppearanceRequestDto dto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(rejectedRequest);
        return new ResponseEntity<>(new Result(true, HttpStatusCode.SUCCESS, "Request Rejected", dto), HttpStatus.OK);
    }

    // Endpoint to request a SuperFrog appearance for TCU events
    @PostMapping("/api/tcu/appearances")
    public ResponseEntity<Result> requestTcuAppearance(@Valid @RequestBody SuperFrogAppearanceRequestDto requestDto) {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(requestDto);
        // Additional business logic to handle TCU specific rules can be implemented here
        request = superFrogAppearanceRequestService.EventRequest(request);
        SuperFrogAppearanceRequestDto dto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(request);
        return new ResponseEntity<>(new Result(true, HttpStatusCode.SUCCESS, "TCU Event Request Created", dto), HttpStatus.CREATED);
    }

    @GetMapping("/api/appearances/{requestId}")
    public ResponseEntity<?> viewAppearanceRequest(@PathVariable Integer requestId) {
        try {
            SuperFrogAppearanceRequest appearanceRequest = superFrogAppearanceRequestService.findById(requestId);
            if (appearanceRequest == null) {
                return ResponseEntity.notFound().build();
            }
            SuperFrogAppearanceRequestDto appearanceRequestDto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(appearanceRequest);
            return ResponseEntity.ok(appearanceRequestDto);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving appearance request: " + e.getMessage());
        }
    }


    @PutMapping("/api/appearances/{requestId}")
    public ResponseEntity<Result> editAppearanceRequest(@PathVariable Integer requestId, @Valid @RequestBody SuperFrogAppearanceRequestDto requestDto) {
        try {
            SuperFrogAppearanceRequest updatedRequest = superFrogAppearanceRequestService.editAppearanceRequest(requestId, requestDto);
            SuperFrogAppearanceRequestDto updatedRequestDto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
            return new ResponseEntity<>(new Result(true, HttpStatusCode.SUCCESS, "Request successfully updated", updatedRequestDto), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(new Result(false, HttpStatusCode.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(false, HttpStatusCode.INTERNAL_SERVER_ERROR, "Failed to update the request: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}