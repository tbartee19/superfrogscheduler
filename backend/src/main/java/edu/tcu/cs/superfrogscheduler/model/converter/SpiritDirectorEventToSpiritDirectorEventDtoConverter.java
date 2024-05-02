package edu.tcu.cs.superfrogscheduler.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.superfrogscheduler.model.SpiritDirectorEvent;
import edu.tcu.cs.superfrogscheduler.model.dto.SpiritDirectorEventDto;

@Component
public class SpiritDirectorEventToSpiritDirectorEventDtoConverter implements Converter<SpiritDirectorEvent, SpiritDirectorEventDto>{
    
    @Override
    public SpiritDirectorEventDto convert(SpiritDirectorEvent source){
        return new SpiritDirectorEventDto(
            source.getId(),
            source.getEventTitle(), 
            source.getStartDate(), 
            source.getStartTime(), 
            source.getEndDate(), 
            source.getEndTime(), 
            source.getRecurrenceStart(), 
            source.getRecurrenceEnd());
    }

}
