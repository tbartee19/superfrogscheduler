package edu.tcu.cs.superfrogscheduler.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.superfrogscheduler.model.SpiritDirectorEvent;
import edu.tcu.cs.superfrogscheduler.model.converter.SpiritDirectorEventDtoToSpiritDirectorEventConverter;
import edu.tcu.cs.superfrogscheduler.model.converter.SpiritDirectorEventToSpiritDirectorEventDtoConverter;
import edu.tcu.cs.superfrogscheduler.model.dto.SpiritDirectorEventDto;
import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.SpiritDirectorEventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpiritDirectorEventController {
    private final SpiritDirectorEventService spiritDirectorEventService;
    private final SpiritDirectorEventToSpiritDirectorEventDtoConverter spiritDirectorEventToSpiritDirectorEventDtoConverter;
    private final SpiritDirectorEventDtoToSpiritDirectorEventConverter spiritDirectorEventDtoToSpiritDirectorEventConverter;

    public SpiritDirectorEventController(SpiritDirectorEventService spiritDirectorEventService, SpiritDirectorEventToSpiritDirectorEventDtoConverter spiritDirectorEventToSpiritDirectorEventDtoConverter, SpiritDirectorEventDtoToSpiritDirectorEventConverter spiritDirectorEventDtoToSpiritDirectorEventConverter) {
        this.spiritDirectorEventService = spiritDirectorEventService;
        this.spiritDirectorEventToSpiritDirectorEventDtoConverter = spiritDirectorEventToSpiritDirectorEventDtoConverter;
        this.spiritDirectorEventDtoToSpiritDirectorEventConverter = spiritDirectorEventDtoToSpiritDirectorEventConverter;
    }
    
    // use case 17 - Spirit Director can add an event
    @PostMapping("/api/spirit-director-events")
    public Result addSpiritDirectorEvent(@Valid @RequestBody SpiritDirectorEventDto spiritDirectorEventDto){
        SpiritDirectorEvent newEvent = this.spiritDirectorEventDtoToSpiritDirectorEventConverter.convert(spiritDirectorEventDto);
        SpiritDirectorEvent savedEvent = this.spiritDirectorEventService.save(newEvent);
        SpiritDirectorEventDto savedEventDto = this.spiritDirectorEventToSpiritDirectorEventDtoConverter.convert(savedEvent);
        return new Result(true, HttpStatusCode.SUCCESS, "Add Success", savedEventDto);
    }

    // use case 17 - Spirit Director can update an event
    @PutMapping("/api/spirit-director-events/{id}")
    public Result updateSpiritDirectorEvent(@PathVariable Integer id, @Valid @RequestBody SpiritDirectorEventDto spiritDirectorEventDto) {
        SpiritDirectorEvent update = this.spiritDirectorEventDtoToSpiritDirectorEventConverter.convert(spiritDirectorEventDto);
        SpiritDirectorEvent updatedEvent = this.spiritDirectorEventService.update(id, update);
        SpiritDirectorEventDto updatedEventDto = this.spiritDirectorEventToSpiritDirectorEventDtoConverter.convert(updatedEvent);
        return new Result(true, HttpStatusCode.SUCCESS, "Update Success", updatedEventDto);
    }

    // use case 17 - Spirit Director can delete an event
    @DeleteMapping("/api/spirit-director-events/{id}")
    public Result deleteSpiritDirectorEvent(@PathVariable Integer id){
        this.spiritDirectorEventService.delete(id);
        return new Result(true, HttpStatusCode.SUCCESS, "Delete Success");
    }


    // find a Spirit Director event by id
    @GetMapping("/api/spirit-director-events/{id}")
    public Result findEventById(@PathVariable int id) {
        SpiritDirectorEvent foundEvent = this.spiritDirectorEventService.findById(id);
        SpiritDirectorEventDto eventDto = this.spiritDirectorEventToSpiritDirectorEventDtoConverter.convert(foundEvent);
        return new Result(true, HttpStatusCode.SUCCESS, "Find One Success", eventDto);
    }

    // get all Spirit Director events
    @GetMapping("/api/spirit-director-events")
    public Result findAllSpiritDirectorEvents() {
        List<SpiritDirectorEvent> foundEvent = this.spiritDirectorEventService.findAll();

        List<SpiritDirectorEventDto> foundEventDtos = foundEvent.stream()
                .map(this.spiritDirectorEventToSpiritDirectorEventDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, HttpStatusCode.SUCCESS, "Find All Success", foundEventDtos);
    }

}