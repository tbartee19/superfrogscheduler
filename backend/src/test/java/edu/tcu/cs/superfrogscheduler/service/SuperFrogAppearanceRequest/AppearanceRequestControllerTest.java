package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import static org.mockito.BDDMockito.given;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.superfrogscheduler.controller.AppearanceRequestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import edu.tcu.cs.superfrogscheduler.model.EventType;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // turn off Spring Security
public class AppearanceRequestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    @Autowired
    ObjectMapper objectMapper;

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
        sfar1.setEventType(EventType.PRIVATE);
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
        sfar2.setEventType(EventType.TCU);
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
        sfar3.setEventType(EventType.PUBLIC);
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

    @Test
    void testAddAppearanceRequestSuccess() throws Exception {
        // Given
        SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(null,
                "Kate",
                "Bednarz",
                "(777) 777-7777",
                "kateabednarz@gmail.com",
                EventType.PRIVATE,
                "Senior Pictures",
                "Kate Bednarz",
                "2901 Stadium Drive, Fort Worth TX 76109",
                "yes",
                "N/A",
                "N/A",
                "N/A",
                "personal pictures",
                RequestStatus.PENDING);
        String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);

        SuperFrogAppearanceRequest savedAppearanceRequest = new SuperFrogAppearanceRequest();
        savedAppearanceRequest.setRequestId(123);
        savedAppearanceRequest.setContactFirstName("Kate");
        savedAppearanceRequest.setContactLastName("Bednarz");
        savedAppearanceRequest.setPhoneNumber("(777) 777-7777");
        savedAppearanceRequest.setEmail("kateabednarz@gmail.com");
        savedAppearanceRequest.setEventType(EventType.PRIVATE);
        savedAppearanceRequest.setEventTitle("Senior Pictures");
        savedAppearanceRequest.setNameOfOrg("Kate Bednarz");
        savedAppearanceRequest.setAddress("2901 Stadium Drive, Fort Worth TX 76109");
        savedAppearanceRequest.setIsOnTCUCampus("yes");
        savedAppearanceRequest.setSpecialInstructions("N/A");
        savedAppearanceRequest.setExpenses("N/A");
        savedAppearanceRequest.setOutsideOrgs("N/A");
        savedAppearanceRequest.setDescription("personal pictures");
        savedAppearanceRequest.setStatus(RequestStatus.PENDING);

        given(this.superFrogAppearanceRequestService.save(Mockito.any(SuperFrogAppearanceRequest.class))).willReturn(savedAppearanceRequest);

        // When and then
        this.mockMvc
                .perform(post("/api/superfrogappearancerequests").contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.requestId").isNotEmpty())
                .andExpect(jsonPath("$.data.contactFirstName").value(savedAppearanceRequest.getContactFirstName()))
                .andExpect(jsonPath("$.data.contactLastName").value(savedAppearanceRequest.getContactLastName()))
                .andExpect(jsonPath("$.data.phoneNumber").value(savedAppearanceRequest.getPhoneNumber()))
                .andExpect(jsonPath("$.data.email").value(savedAppearanceRequest.getEmail()))
                .andExpect(jsonPath("$.data.eventType").value(savedAppearanceRequest.getEventType()))
                .andExpect(jsonPath("$.data.eventTitle").value(savedAppearanceRequest.getEventTitle()))
                .andExpect(jsonPath("$.data.nameOfOrg").value(savedAppearanceRequest.getNameOfOrg()))
                .andExpect(jsonPath("$.data.address").value(savedAppearanceRequest.getAddress()))
                .andExpect(jsonPath("$.data.isOnTCUCampus").value(savedAppearanceRequest.getIsOnTCUCampus()))
                .andExpect(jsonPath("$.data.specialInstructions").value(savedAppearanceRequest.getSpecialInstructions()))
                .andExpect(jsonPath("$.data.expenses").value(savedAppearanceRequest.getExpenses()))
                .andExpect(jsonPath("$.data.outsideOrgs").value(savedAppearanceRequest.getOutsideOrgs()))
                .andExpect(jsonPath("$.data.description").value(savedAppearanceRequest.getDescription()))
                .andExpect(jsonPath("$.data.status").value(savedAppearanceRequest.getStatus()));


    }
}
