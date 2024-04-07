package edu.tcu.cs.superfrogscheduler.model.converter;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter implements Converter<SuperFrogAppearanceRequest, SuperFrogAppearanceRequestDto> {
//    private final SuperfrogToSuperfrogDtoConverter superfrogToSuperfrogDtoConverter;
//    public SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter(SuperfrogToSuperfrogDtoConverter superfrogToSuperfrogDtoConverter) {
//        this.superfrogToSuperfrogDtoConverter = superfrogToSuperfrogDtoConverter;
//    }

    @Override
    public SuperFrogAppearanceRequestDto convert(SuperFrogAppearanceRequest source) {
        return new SuperFrogAppearanceRequestDto(
                source.getRequestId(),
                source.getContactFirstName(),
                source.getContactLastName(),
                source.getPhoneNumber(),
                source.getEmail(),
                source.getEventType(),
                source.getEventTitle(),
                source.getNameOfOrg(),
                source.getAddress(),
                source.getIsOnTCUCampus(),
                source.getSpecialInstructions(),
                source.getExpenses(),
                source.getOutsideOrgs(),
                source.getDescription(),
                source.getStatus());
                // source.getStudent() != null ? this.superfrogToSuperfrogDtoConverter.convert(source.getStudent()) : null);
    }
}
