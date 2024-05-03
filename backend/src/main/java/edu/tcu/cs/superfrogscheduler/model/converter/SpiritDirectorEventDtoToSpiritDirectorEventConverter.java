package edu.tcu.cs.superfrogscheduler.model.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.superfrogscheduler.model.SpiritDirectorEvent;
import edu.tcu.cs.superfrogscheduler.model.dto.SpiritDirectorEventDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class SpiritDirectorEventDtoToSpiritDirectorEventConverter implements Converter<SpiritDirectorEventDto, SpiritDirectorEvent>{

    @Override
    public SpiritDirectorEvent convert(SpiritDirectorEventDto source){
        SpiritDirectorEvent spiritDirectorEvent = new SpiritDirectorEvent();
        spiritDirectorEvent.setEventTitle(source.eventTitle());
        spiritDirectorEvent.setStartDate(source.startDate());
        spiritDirectorEvent.setStartTime(source.startTime());
        spiritDirectorEvent.setEndDate(source.endDate());
        spiritDirectorEvent.setEndTime(source.endTime());
        spiritDirectorEvent.setRecurrenceStart(source.recurrenceStart());
        spiritDirectorEvent.setRecurrenceEnd(source.recurrenceEnd());
        return spiritDirectorEvent;
    }

}
