package edu.tcu.cs.superfrogscheduler.controller;

import javax.validation.Valid;

import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import org.springframework.web.bind.annotation.*;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;


// AppearanceRequestController
// manages appearance requests
// this could include creating, editing, viewing, and deleting requests
// as well as approval/rejection by the Spirit Director.

// use Cases 1-12, 24, 25, 26 i believe


@RestController
@RequestMapping("/api/appearances")
public class AppearanceRequestController {
    // class variables
    private final SuperFrogAppearanceRequestService superFrogAppearanceRequestService;
    private final SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
    private final SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

    // constructor
    public AppearanceRequestController(SuperFrogAppearanceRequestService superFrogAppearanceRequestService, SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter, SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter){
        this.superFrogAppearanceRequestService = superFrogAppearanceRequestService;
        this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter = superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter;
        this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter = superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
    }


    // use case 1 - Customer requests a SuperFrog appearance
    @PostMapping("/api/superfrogappearancerequests")
    public Result addSuperFrogAppearanceRequest(@Valid @RequestBody SuperFrogAppearanceRequestDto appearanceRequestDto){
        SuperFrogAppearanceRequest newAppearance = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(appearanceRequestDto);
        SuperFrogAppearanceRequest savedAppearance = this.superFrogAppearanceRequestService.save(newAppearance);
        SuperFrogAppearanceRequestDto savedAppearanceDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(savedAppearance);
        return new Result(true, HttpStatusCode.SUCCESS, "Add Success", savedAppearanceDto);
    }


    // use case 2 - Customer edits request details
    @PutMapping("/api/superfrogappearancerequests/{requestId}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @Valid @RequestBody SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto){
        SuperFrogAppearanceRequest update = this.superFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter.convert(superFrogAppearanceRequestDto);
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.update(requestId, update);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Success", updatedRequestDto);
    }

    @PutMapping("/api/superfrogappearancerequests/{requestId}/status/{status}")
    public Result updateSuperFrogAppearanceRequest(@PathVariable Integer requestId, @PathVariable RequestStatus status){
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.updateStatus(requestId, status);
        SuperFrogAppearanceRequestDto updatedRequestDto = this.superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Status Success", updatedRequestDto);
    }


    // use case 3 - Customer cancels a submitted request
    @DeleteMapping("/api/superfrogappearancerequests/{requestId}")
    public Result deleteSuperFrogAppearanceRequest(@PathVariable Integer requestId){
        this.superFrogAppearanceRequestService.delete(requestId);
        return new Result(true, HttpStatusCode.SUCCESS, "Delete Success");
    }

    // other methods
}
