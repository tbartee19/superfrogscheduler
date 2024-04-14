package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;

@ExtendWith(MockitoExtension.class)
public class AppearanceRequestServiceTest {
    
    @Mock
    SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

    // @Mock
    // IdWorker idWorker;

    @InjectMocks
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    List<SuperFrogAppearanceRequest> superFrogAppearanceRequests;

    @BeforeEach
    void setUp(){
        this.superFrogAppearanceRequests = new ArrayList<>();

        SuperFrogAppearanceRequest sfar1 = new SuperFrogAppearanceRequest();
        sfar1.setRequestId(1);
        sfar1.setContactFirstName("John");
        sfar1.setContactLastName("Doe");
        sfar1.setPhoneNumber("(123) 456-7890");
        sfar1.setEmail("johndoe@gmail.com");
        sfar1.setEventType("PRIVATE");
        sfar1.setEventTitle("wedding");
        sfar1.setNameOfOrg("wedding company");
        sfar1.setAddress("200 Texas Street, Fort Worth TX 76102");
        sfar1.setIsOnTCUCampus("no");
        sfar1.setDescription("afternoon wedding");
        this.superFrogAppearanceRequests.add(sfar1);

        SuperFrogAppearanceRequest sfar2 = new SuperFrogAppearanceRequest();
        sfar2.setRequestId(2);
        sfar2.setContactFirstName("Tom");
        sfar2.setContactLastName("Smith");
        sfar2.setPhoneNumber("(999) 999-9999");
        sfar2.setEmail("tomsmith@gmail.com");
        sfar2.setEventType("TCU");
        sfar2.setEventTitle("game day");
        sfar2.setNameOfOrg("TCU");
        sfar2.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        sfar2.setIsOnTCUCampus("yes");
        sfar2.setDescription("football game");
        this.superFrogAppearanceRequests.add(sfar2);

        SuperFrogAppearanceRequest sfar3 = new SuperFrogAppearanceRequest();
        sfar3.setRequestId(3);
        sfar3.setContactFirstName("Fred");
        sfar3.setContactLastName("Johnson");
        sfar3.setPhoneNumber("(888) 888-888");
        sfar3.setEmail("fredjohnson@gmail.com");
        sfar3.setEventType("PUBLIC");
        sfar3.setEventTitle("school visit");
        sfar3.setNameOfOrg("Alice E. Carlson Elementary School");
        sfar3.setAddress("3320 W Cantey St, Fort Worth TX 76109");
        sfar3.setIsOnTCUCampus("no");
        sfar3.setDescription("school assembly");
        this.superFrogAppearanceRequests.add(sfar3);
    }

    @AfterEach
    void tearDown(){
        
    }

}
