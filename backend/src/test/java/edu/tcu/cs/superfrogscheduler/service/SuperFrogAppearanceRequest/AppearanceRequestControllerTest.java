package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // turn off Spring Security
public class AppearanceRequestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    @MockBean
    SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;

    @Autowired
    ObjectMapper objectMapper;

    List<SuperFrogAppearanceRequest> superFrogAppearanceRequests;

    @BeforeEach
    void setUp() {
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
        sfar3.setDescription("school assembly");
        this.superFrogAppearanceRequests.add(sfar3);

    }

    @AfterEach
    void tearDown() {

    }

//     @Test
//     void testAddAppearanceRequestSuccess() throws Exception {
//         // Given
//         SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(null,
//                 "Kate",
//                 "Bednarz",
//                 "(777) 777-7777",
//                 "kateabednarz@gmail.com",
//                 "PRIVATE",
//                 "Senior Pictures",
//                 "Kate Bednarz",
//                 "2901 Stadium Drive, Fort Worth TX 76109",
//                 "yes",
//                 "N/A",
//                 "N/A",
//                 "N/A",
//                 "personal pictures",
//                 RequestStatus.PENDING);
//         String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);

//         SuperFrogAppearanceRequest savedAppearanceRequest = new SuperFrogAppearanceRequest();
//         savedAppearanceRequest.setRequestId(123);
//         savedAppearanceRequest.setContactFirstName("Kate");
//         savedAppearanceRequest.setContactLastName("Bednarz");
//         savedAppearanceRequest.setPhoneNumber("(777) 777-7777");
//         savedAppearanceRequest.setEmail("kateabednarz@gmail.com");
//         savedAppearanceRequest.setEventType("PRIVATE");
//         savedAppearanceRequest.setEventTitle("Senior Pictures");
//         savedAppearanceRequest.setNameOfOrg("Kate Bednarz");
//         savedAppearanceRequest.setAddress("2901 Stadium Drive, Fort Worth TX 76109");
//         savedAppearanceRequest.setSpecialInstructions("N/A");
//         savedAppearanceRequest.setExpenses("N/A");
//         savedAppearanceRequest.setOutsideOrgs("N/A");
//         savedAppearanceRequest.setDescription("personal pictures");
//         savedAppearanceRequest.setStatus(RequestStatus.PENDING);

//         given(this.superFrogAppearanceRequestService.save(Mockito.any(SuperFrogAppearanceRequest.class)))
//                 .willReturn(savedAppearanceRequest);

//         // When and then
//         this.mockMvc
//                 .perform(post("/api/appearances").contentType(MediaType.APPLICATION_JSON).content(json)
//                         .accept(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.flag").value(true))
//                 .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                 .andExpect(jsonPath("$.message").value("Add Success"))
//                 .andExpect(jsonPath("$.data.requestId").isNotEmpty())
//                 .andExpect(jsonPath("$.data.contactFirstName").value(savedAppearanceRequest.getContactFirstName()))
//                 .andExpect(jsonPath("$.data.contactLastName").value(savedAppearanceRequest.getContactLastName()))
//                 .andExpect(jsonPath("$.data.phoneNumber").value(savedAppearanceRequest.getPhoneNumber()))
//                 .andExpect(jsonPath("$.data.email").value(savedAppearanceRequest.getEmail()))
//                 .andExpect(jsonPath("$.data.eventType").value(savedAppearanceRequest.getEventType().toString()))
//                 .andExpect(jsonPath("$.data.eventTitle").value(savedAppearanceRequest.getEventTitle()))
//                 .andExpect(jsonPath("$.data.nameOfOrg").value(savedAppearanceRequest.getNameOfOrg()))
//                 .andExpect(jsonPath("$.data.address").value(savedAppearanceRequest.getAddress()))
//                 .andExpect(jsonPath("$.data.isOnTCUCampus").value(savedAppearanceRequest.getIsOnTCUCampus()))
//                 .andExpect(
//                         jsonPath("$.data.specialInstructions").value(savedAppearanceRequest.getSpecialInstructions()))
//                 .andExpect(jsonPath("$.data.expenses").value(savedAppearanceRequest.getExpenses()))
//                 .andExpect(jsonPath("$.data.outsideOrgs").value(savedAppearanceRequest.getOutsideOrgs()))
//                 .andExpect(jsonPath("$.data.description").value(savedAppearanceRequest.getDescription()));
//                 // didn't check status

//     }

     @Test
     void testUpdateAppearanceRequestSuccess() throws Exception {
         // Given
         SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(2,
                 LocalDate.now(),
                 LocalTime.of(10,0),
                 LocalTime.of(11, 0),
                 "Tom",
                 "Smith",
                 "(000) 000-0000",
                 "tomsmith@gmail.com",
                 "TCU",
                 "game day",
                 "TCU",
                 "2850 Stadium Drive, Fort Worth TX 76109",
                 "yes",
                 "N/A",
                 "N/A",
                 "football game",
                 RequestStatus.PENDING);
         String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);

         SuperFrogAppearanceRequest updatedAppearanceRequest = new SuperFrogAppearanceRequest();
         updatedAppearanceRequest.setRequestId(2);
         updatedAppearanceRequest.setContactFirstName("Tom");
         updatedAppearanceRequest.setContactLastName("Smith");
         updatedAppearanceRequest.setPhoneNumber("(000) 000-0000");
         updatedAppearanceRequest.setEmail("tomsmith@gmail.com");
         updatedAppearanceRequest.setEventType("TCU");
         updatedAppearanceRequest.setEventTitle("game day");
         updatedAppearanceRequest.setNameOfOrg("TCU");
         updatedAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
         updatedAppearanceRequest.setSpecialInstructions("N/A");
         updatedAppearanceRequest.setExpenses("N/A");
         updatedAppearanceRequest.setOutsideOrgs("N/A");
         updatedAppearanceRequest.setDescription("football game");
         updatedAppearanceRequest.setStatus(RequestStatus.PENDING);

         given(this.superFrogAppearanceRequestService.update(eq(2), Mockito.any(SuperFrogAppearanceRequest.class)))
                 .willReturn(updatedAppearanceRequest);

         // When and then
         this.mockMvc
                 .perform(put("/api/appearances/2").contentType(MediaType.APPLICATION_JSON)
                         .content(json).accept(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.flag").value(true))
                 .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
                 .andExpect(jsonPath("$.message").value("Update Success"))
                 .andExpect(jsonPath("$.data.requestId").isNotEmpty())
                 .andExpect(jsonPath("$.data.contactFirstName").value(updatedAppearanceRequest.getContactFirstName()))
                 .andExpect(jsonPath("$.data.contactLastName").value(updatedAppearanceRequest.getContactLastName()))
                 .andExpect(jsonPath("$.data.phoneNumber").value(updatedAppearanceRequest.getPhoneNumber()))
                 .andExpect(jsonPath("$.data.email").value(updatedAppearanceRequest.getEmail()))
                 .andExpect(jsonPath("$.data.eventType").value(updatedAppearanceRequest.getEventType().toString()))
                 .andExpect(jsonPath("$.data.eventTitle").value(updatedAppearanceRequest.getEventTitle()))
                 .andExpect(jsonPath("$.data.nameOfOrg").value(updatedAppearanceRequest.getNameOfOrg()))
                 .andExpect(jsonPath("$.data.address").value(updatedAppearanceRequest.getAddress()))
                 .andExpect(jsonPath("$.data.specialInstructions").value(updatedAppearanceRequest.getSpecialInstructions()))
                 .andExpect(jsonPath("$.data.expenses").value(updatedAppearanceRequest.getExpenses()))
                 .andExpect(jsonPath("$.data.outsideOrgs").value(updatedAppearanceRequest.getOutsideOrgs()))
                 .andExpect(jsonPath("$.data.description").value(updatedAppearanceRequest.getDescription()))
                 .andExpect(jsonPath("$.data.status").value(updatedAppearanceRequest.getStatus().toString()));
     }

//     @Test
//     void testReverseApprovedDecisionSuccess() throws Exception {
//        // Given
//         SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(2,
//                 LocalDate.now(),
//                 LocalTime.of(10,0),
//                 LocalTime.of(11, 0),
//                 "Tom",
//                 "Smith",
//                 "(000) 000-0000",
//                 "tomsmith@gmail.com",
//                 "TCU",
//                 "game day",
//                 "TCU",
//                 "2850 Stadium Drive, Fort Worth TX 76109",
//                 "yes",
//                 "N/A",
//                 "N/A",
//                 "football game",
//                 RequestStatus.APPROVED);
//         String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);
//
//         SuperFrogAppearanceRequest updatedAppearanceRequest = new SuperFrogAppearanceRequest();
//         updatedAppearanceRequest.setRequestId(2);
//         updatedAppearanceRequest.setContactFirstName("Tom");
//         updatedAppearanceRequest.setContactLastName("Smith");
//         updatedAppearanceRequest.setPhoneNumber("(000) 000-0000");
//         updatedAppearanceRequest.setEmail("tomsmith@gmail.com");
//         updatedAppearanceRequest.setEventType("TCU");
//         updatedAppearanceRequest.setEventTitle("game day");
//         updatedAppearanceRequest.setNameOfOrg("TCU");
//         updatedAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
//         updatedAppearanceRequest.setSpecialInstructions("N/A");
//         updatedAppearanceRequest.setExpenses("N/A");
//         updatedAppearanceRequest.setOutsideOrgs("N/A");
//         updatedAppearanceRequest.setDescription("football game");
//         updatedAppearanceRequest.setStatus(RequestStatus.APPROVED);
//
//         given(this.superFrogAppearanceRequestService.reverseDecision(eq(2)))
//            .willReturn(updatedAppearanceRequest);
//
//         // When and then
//         this.mockMvc
//                 .perform(put("/api/appearances/2").contentType(MediaType.APPLICATION_JSON)
//                         .content(json).accept(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.flag").value(true))
//                 .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                 .andExpect(jsonPath("$.message").value("Status reverse success"))
//                 .andExpect(jsonPath("$.data.requestId").isNotEmpty())
//                 .andExpect(jsonPath("$.data.contactFirstName").value(updatedAppearanceRequest.getContactFirstName()))
//                 .andExpect(jsonPath("$.data.contactLastName").value(updatedAppearanceRequest.getContactLastName()))
//                 .andExpect(jsonPath("$.data.phoneNumber").value(updatedAppearanceRequest.getPhoneNumber()))
//                 .andExpect(jsonPath("$.data.email").value(updatedAppearanceRequest.getEmail()))
//                 .andExpect(jsonPath("$.data.eventType").value(updatedAppearanceRequest.getEventType().toString()))
//                 .andExpect(jsonPath("$.data.eventTitle").value(updatedAppearanceRequest.getEventTitle()))
//                 .andExpect(jsonPath("$.data.nameOfOrg").value(updatedAppearanceRequest.getNameOfOrg()))
//                 .andExpect(jsonPath("$.data.address").value(updatedAppearanceRequest.getAddress()))
//                 .andExpect(jsonPath("$.data.specialInstructions").value(updatedAppearanceRequest.getSpecialInstructions()))
//                 .andExpect(jsonPath("$.data.expenses").value(updatedAppearanceRequest.getExpenses()))
//                 .andExpect(jsonPath("$.data.outsideOrgs").value(updatedAppearanceRequest.getOutsideOrgs()))
//                 .andExpect(jsonPath("$.data.description").value(updatedAppearanceRequest.getDescription()))
//                 .andExpect(jsonPath("$.data.status").value(updatedAppearanceRequest.getStatus().toString()));
//
//     }

        @Test
        @WithMockUser(username="admin", roles={"ADMIN"})
        public void testReverseAppearanceDecision_Success() throws Exception {
            // Given
            Integer requestId = 1;
            SuperFrogAppearanceRequest updatedRequest = new SuperFrogAppearanceRequest();
            updatedRequest.setRequestId(requestId);
            updatedRequest.setStatus(RequestStatus.REJECTED);

            SuperFrogAppearanceRequestDto updatedRequestDto = new SuperFrogAppearanceRequestDto(1,
                    LocalDate.now(),
                    LocalTime.of(10,0),
                    LocalTime.of(11, 0),
                    "Tom",
                    "Smith",
                    "(000) 000-0000",
                    "tomsmith@gmail.com",
                    "TCU",
                    "game day",
                    "TCU",
                    "2850 Stadium Drive, Fort Worth TX 76109",
                    "yes",
                    "N/A",
                    "N/A",
                    "football game",
                    RequestStatus.APPROVED);

            when(superFrogAppearanceRequestService.reverseDecision(eq(requestId))).thenReturn(updatedRequest);
            when(superFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter.convert(updatedRequest)).thenReturn(updatedRequestDto);

            // When & Then
            mockMvc.perform(put("/api/appearance/{requestId}/status/{status}", requestId, "REJECTED")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.flag").value(true))
                    .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
                    .andExpect(jsonPath("$.message").value("Status reverse success"))
                    .andExpect(jsonPath("$.data.requestId").value(updatedRequestDto.requestId()));
        }

//     @Test
//     void testUpdateAppearanceRequestWithNonExistentId() throws Exception {
//         // Given
//         SuperFrogAppearanceRequestDto appearanceRequestDto = new SuperFrogAppearanceRequestDto(123456,
//                 LocalDate.now(),
//                 LocalTime.of(10, 0),
//                 LocalTime.of(11, 0),
//                 "First",
//                 "Last",
//                 "(333) 333-3333",
//                 "email@gmail.com",
//                 "TCU",
//                 "event title",
//                 "name of org",
//                 "2850 Stadium Drive, Fort Worth TX 76109",
//                 "yes",
//                 "N/A",
//                 "N/A",
//                 "description",
//                 RequestStatus.PENDING);
//         String json = this.objectMapper.writeValueAsString(appearanceRequestDto);
//
//
//         given(this.superFrogAppearanceRequestService.update(eq(123456), Mockito.any(SuperFrogAppearanceRequest.class)))
//                 .willThrow(new ObjectNotFoundException("superfrogappearancerequest", 123456));
//
//         // When and then
//         this.mockMvc
//                 .perform(put("/api/appearances/123456").contentType(MediaType.APPLICATION_JSON)
//                         .content(json).accept(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.flag").value(false))
//                 .andExpect(jsonPath("$.code").value(HttpStatusCode.NOT_FOUND))
//                 .andExpect(jsonPath("$.message").value("Could not find superfrogappearancerequest with Id 123456 :("))
//                 .andExpect(jsonPath("$.data").isEmpty());
//     }

//     @Test
//     void testDeleteAppearanceRequestSuccess() throws Exception {
//         // Given
//         doNothing().when(this.superFrogAppearanceRequestService).delete(123456);

//         // When and then
//         this.mockMvc.perform(delete("/api/appearances/123456").accept(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.flag").value(true))
//                 .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                 .andExpect(jsonPath("$.message").value("Delete Success"))
//                 .andExpect(jsonPath("$.data").isEmpty());
//     }

//     @Test
//     void testDeleteAppearanceRequestWithNonExistentId() throws Exception {
//         // Given
//         doThrow(new ObjectNotFoundException("superfrogappearancerequest", 777)).when(this.superFrogAppearanceRequestService)
//                 .delete(777);

//         // When and then
//         this.mockMvc.perform(delete("/api/appearances/777").accept(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.flag").value(false))
//                 .andExpect(jsonPath("$.code").value(HttpStatusCode.NOT_FOUND))
//                 .andExpect(jsonPath("$.message").value("Could not find superfrogappearancerequest with Id 777 :("))
//                 .andExpect(jsonPath("$.data").isEmpty());
//     }
}
