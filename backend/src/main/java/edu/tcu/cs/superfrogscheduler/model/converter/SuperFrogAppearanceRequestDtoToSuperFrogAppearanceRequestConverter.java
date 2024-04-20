package edu.tcu.cs.superfrogscheduler.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.model.EventType;

@Component
public class SuperFrogAppearanceRequestDtoToSuperFrogAppearanceRequestConverter
        implements Converter<SuperFrogAppearanceRequestDto, SuperFrogAppearanceRequest> {

    private EventType TCU = EventType.TCU;

    @Override
    public SuperFrogAppearanceRequest convert(SuperFrogAppearanceRequestDto source) {
        SuperFrogAppearanceRequest superFrogAppearanceRequest = new SuperFrogAppearanceRequest();
        superFrogAppearanceRequest.setRequestId(source.requestId());
        superFrogAppearanceRequest.setEventDate(source.eventDate());
        superFrogAppearanceRequest.setStartTime(source.startTime());
        superFrogAppearanceRequest.setEndTime(source.endTime());
        superFrogAppearanceRequest.setContactFirstName(source.contactFirstName());
        superFrogAppearanceRequest.setContactLastName(source.contactLastName());
        superFrogAppearanceRequest.setPhoneNumber(source.phoneNumber());
        superFrogAppearanceRequest.setEmail(source.email());
        superFrogAppearanceRequest.setEventType(source.eventType());
        superFrogAppearanceRequest.setEventTitle(source.eventTitle());
        superFrogAppearanceRequest.setNameOfOrg(source.nameOfOrg());
        superFrogAppearanceRequest.setAddress(source.address());
        superFrogAppearanceRequest.setSpecialInstructions(source.specialInstructions());
        superFrogAppearanceRequest.setExpenses(source.expenses());
        superFrogAppearanceRequest.setOutsideOrgs(source.outsideOrgs());
        superFrogAppearanceRequest.setDescription(source.description());
        // superFrogAppearanceRequest.setStatus(source.getStatus());
        return superFrogAppearanceRequest;
    }
}
