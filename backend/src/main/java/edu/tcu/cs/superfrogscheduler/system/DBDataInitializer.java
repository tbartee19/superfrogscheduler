package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private SuperFrogAppearanceRequestRepository requestRepository;

    public DBDataInitializer(SuperFrogAppearanceRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        SuperFrogAppearanceRequest sfar1 = new SuperFrogAppearanceRequest(5,
                LocalDate.of(2024, 10, 10),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "First",
                "Last",
                "(111) 111-1111",
                "email@gmail.com",
                EventType.TCU,
                "event title",
                "name of org",
                "2800 S University Dr, Fort Worth TX 76109",
                "special instructions",
                "expenses",
                "outside orgs",
                "description",
                RequestStatus.PENDING);
        SuperFrogAppearanceRequest sfar2 = new SuperFrogAppearanceRequest(6,
                LocalDate.of(2024, 11, 11),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                "First2",
                "Last2",
                "(222) 222-2222",
                "email2@gmail.com",
                EventType.PUBLIC,
                "event title 2",
                "name of org 2",
                "2901 Stadium Dr, Fort Worth TX 76109",
                "special instructions 2",
                "expenses 2",
                "outside orgs 2",
                "description 2",
                RequestStatus.PENDING);
        SuperFrogAppearanceRequest sfar3 = new SuperFrogAppearanceRequest(6,
                LocalDate.of(2024, 12, 12),
                LocalTime.of(18, 0),
                LocalTime.of(19, 0),
                "First3",
                "Last3",
                "(333) 333-3333",
                "email3@gmail.com",
                EventType.PRIVATE,
                "event title 3",
                "name of org 3",
                "2850 Stadium Dr, Fort Worth TX 76109",
                "special instructions 3",
                "expenses 3",
                "outside orgs 3",
                "description 3",
                RequestStatus.PENDING);

    }
}