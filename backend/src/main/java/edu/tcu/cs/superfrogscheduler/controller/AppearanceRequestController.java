package edu.tcu.cs.superfrogscheduler.controller;

import javax.validation.Valid;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
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

import java.time.LocalDate;
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

    // Inside your AppearanceRequestController class

    private void updateExistingRequestFromDto(SuperFrogAppearanceRequest existingRequest, SuperFrogAppearanceRequestDto requestDto) {
        existingRequest.setContactFirstName(requestDto.contactFirstName());
        existingRequest.setContactLastName(requestDto.contactLastName());
        existingRequest.setPhoneNumber(requestDto.phoneNumber());
        existingRequest.setEmail(requestDto.email());
        existingRequest.setEventType(requestDto.eventType());
        existingRequest.setEventTitle(requestDto.eventTitle());
        existingRequest.setNameOfOrg(requestDto.nameOfOrg());
        existingRequest.setAddress(requestDto.address());
        existingRequest.setSpecialInstructions(requestDto.specialInstructions());
        existingRequest.setExpenses(requestDto.expenses());
        existingRequest.setOutsideOrgs(requestDto.outsideOrgs());
        existingRequest.setDescription(requestDto.description());
        existingRequest.setStatus(requestDto.status()); // Assumes your entity supports direct status setting
    }

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
    @PutMapping("/api/appearance/{requestId}/reverse")
    public Result reverseAppearanceDecision(@PathVariable Integer requestId){
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.reverseDecision(requestId);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Status reverse success", updatedRequestDto);
    }

    // use case 26 - the spirit director marks an appearance as incomplete
    @PutMapping("/api/appearance/{requestId}/incomplete")
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


    @PostMapping("/api/tcu/appearances")
    public ResponseEntity<Result> requestTcuAppearance(@Valid @RequestBody SuperFrogAppearanceRequestDto requestDto) {
        SuperFrogAppearanceRequest request = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(requestDto);
        // Additional business logic to handle TCU specific rules can be implemented here
        request = superFrogAppearanceRequestService.EventRequest(request);
        SuperFrogAppearanceRequestDto dto = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(request);
        return new ResponseEntity<>(new Result(true, HttpStatusCode.SUCCESS, "TCU Event Request Created", dto), HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public List<SuperFrogAppearanceRequest> getRequestsByDateAndTitle(
            @RequestParam("date") LocalDate date,
            @RequestParam("title") String title) {
        return superFrogAppearanceRequestService.getRequestsByDateAndTitle(date, title);
    }

    @GetMapping("/api/appearances/{requestId}/view")
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



    @PutMapping("/api/appearances/{requestId}/edit")
    public ResponseEntity<?> editAppearanceRequest(@PathVariable Integer requestId, @RequestBody SuperFrogAppearanceRequestDto requestDto) {
        try {
            SuperFrogAppearanceRequest existingRequest = superFrogAppearanceRequestService.findById(requestId);
            if (existingRequest == null) {
                return ResponseEntity.notFound().build();
            }
            updateExistingRequestFromDto(existingRequest, requestDto);
            superFrogAppearanceRequestService.save(existingRequest);

            SuperFrogAppearanceRequestDto responseDto = convert(existingRequest);
            return ResponseEntity.ok(responseDto);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating appearance request: " + e.getMessage());
        }
    }

    private SuperFrogAppearanceRequestDto convert(SuperFrogAppearanceRequest source) {
        return new SuperFrogAppearanceRequestDto(
                source.getRequestId(),
                source.getEventDate(),
                source.getStartTime(),
                source.getEndTime(),
                source.getContactFirstName(),
                source.getContactLastName(),
                source.getPhoneNumber(),
                source.getEmail(),
                source.getEventType().toString(),  // Convert EventType enum to String here
                source.getEventTitle(),
                source.getNameOfOrg(),
                source.getAddress(),
                source.getSpecialInstructions(),
                source.getExpenses(),
                source.getOutsideOrgs(),
                source.getDescription(),
                source.getStatus()
        );
    }





    //use case 24 mark an appearance as completed
    @PutMapping("/api/appearances/{requestId}/complete")
    public Result completeAppearance(@PathVariable Integer requestId){
        SuperFrogAppearanceRequest doneRequest = this.superFrogAppearanceRequestService.setComplete(requestId);
        SuperFrogAppearanceRequestDto requestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(doneRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Appearance complete success", requestDto);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SuperFrogAppearanceRequestDto>> searchAppearanceRequests(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignedSuperFrog) {
        List<SuperFrogAppearanceRequest> requests = superFrogAppearanceRequestService.searchAppearanceRequests(startDate, endDate, title, firstName, lastName, status, assignedSuperFrog);
        List<SuperFrogAppearanceRequestDto> dtos = requests.stream()
                .map(superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

//    @PostMapping("/api/appearances/{requestId}/assign/{studentId}")
//    public ResponseEntity<?> assignSuperFrogStudent(@PathVariable Integer requestId, @PathVariable Integer studentId) {
//        try {
//            // Check if the request exists and is in "Approved" status
//            SuperFrogAppearanceRequest request = superFrogAppearanceRequestService.findById(requestId);
//            if (request == null || request.getStatus() != RequestStatus.APPROVED) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Check if the student exists and is available
//            SuperFrogStudent student = superFrogStudentService.findById(studentId);
//            if (student == null || !student.isAvailable()) {
//                return ResponseEntity.badRequest().body("Selected student is not available.");
//            }
//
//            // Assign the student to the request
//            request.setAssignedStudent(student);
//            request.setStatus(RequestStatus.ASSIGNED);
//            superFrogAppearanceRequestService.save(request);
//
//            // Update the student's personal schedule
//            student.addAppearanceRequest(request);
//            superFrogStudentService.save(student);
//
//            // Notify relevant actors
//            notifyRequestAssignment(request);
//
//            return ResponseEntity.ok("SuperFrog Student assigned successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning SuperFrog Student: " + e.getMessage());
//        }
//    }


}

