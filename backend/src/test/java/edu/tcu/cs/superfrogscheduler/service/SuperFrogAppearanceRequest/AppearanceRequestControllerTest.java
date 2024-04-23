package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import edu.tcu.cs.superfrogscheduler.controller.AppearanceRequestController;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // turn off Spring Security
public class AppearanceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    List<SuperFrogAppearanceRequest> superFrogAppearanceRequests;

    @MockBean
    private SuperFrogAppearanceRequestService service;

    @InjectMocks
    private AppearanceRequestController controller;

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
    void tearDown() {

    }


//    @Test
//    void testAddAppearanceRequestSuccess() throws Exception {
//        // Given
//        SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(null,
//                "Kate",
//                "Bednarz",
//                "(777) 777-7777",
//                "kateabednarz@gmail.com",
//                "PRIVATE",
//                "Senior Pictures",
//                "Kate Bednarz",
//                "2901 Stadium Drive, Fort Worth TX 76109",
//                "yes",
//                "N/A",
//                "N/A",
//                "N/A",
//                "personal pictures",
//                RequestStatus.PENDING,
//                "bring flags");
//        String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);
//
//        SuperFrogAppearanceRequest savedAppearanceRequest = new SuperFrogAppearanceRequest();
//        savedAppearanceRequest.setRequestId(123);
//        savedAppearanceRequest.setContactFirstName("Kate");
//        savedAppearanceRequest.setContactLastName("Bednarz");
//        savedAppearanceRequest.setPhoneNumber("(777) 777-7777");
//        savedAppearanceRequest.setEmail("kateabednarz@gmail.com");
//        savedAppearanceRequest.setEventType("PRIVATE");
//        savedAppearanceRequest.setEventTitle("Senior Pictures");
//        savedAppearanceRequest.setNameOfOrg("Kate Bednarz");
//        savedAppearanceRequest.setAddress("2901 Stadium Drive, Fort Worth TX 76109");
//        savedAppearanceRequest.setIsOnTCUCampus("yes");
//        savedAppearanceRequest.setSpecialInstructions("N/A");
//        savedAppearanceRequest.setExpenses("N/A");
//        savedAppearanceRequest.setOutsideOrgs("N/A");
//        savedAppearanceRequest.setDescription("personal pictures");
//        savedAppearanceRequest.setStatus(RequestStatus.PENDING);
//
//        given(this.superFrogAppearanceRequestService.save(any(SuperFrogAppearanceRequest.class)))
//                .willReturn(savedAppearanceRequest);
//
//        // When and then
//        this.mockMvc
//                .perform(post("/api/appearances").contentType(MediaType.APPLICATION_JSON).content(json)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Add Success"))
//                .andExpect(jsonPath("$.data.requestId").isNotEmpty())
//                .andExpect(jsonPath("$.data.contactFirstName").value(savedAppearanceRequest.getContactFirstName()))
//                .andExpect(jsonPath("$.data.contactLastName").value(savedAppearanceRequest.getContactLastName()))
//                .andExpect(jsonPath("$.data.phoneNumber").value(savedAppearanceRequest.getPhoneNumber()))
//                .andExpect(jsonPath("$.data.email").value(savedAppearanceRequest.getEmail()))
//                .andExpect(jsonPath("$.data.eventType").value(savedAppearanceRequest.getEventType().toString()))
//                .andExpect(jsonPath("$.data.eventTitle").value(savedAppearanceRequest.getEventTitle()))
//                .andExpect(jsonPath("$.data.nameOfOrg").value(savedAppearanceRequest.getNameOfOrg()))
//                .andExpect(jsonPath("$.data.address").value(savedAppearanceRequest.getAddress()))
//                .andExpect(jsonPath("$.data.isOnTCUCampus").value(savedAppearanceRequest.getIsOnTCUCampus()))
//                .andExpect(jsonPath("$.data.specialInstructions").value(savedAppearanceRequest.getSpecialInstructions()))
//                .andExpect(jsonPath("$.data.expenses").value(savedAppearanceRequest.getExpenses()))
//                .andExpect(jsonPath("$.data.outsideOrgs").value(savedAppearanceRequest.getOutsideOrgs()))
//                .andExpect(jsonPath("$.data.description").value(savedAppearanceRequest.getDescription()));
//    }
//
//    @Test
//    void testUpdateAppearanceRequestSuccess() throws Exception {
//        // Given
//        SuperFrogAppearanceRequestDto superFrogAppearanceRequestDto = new SuperFrogAppearanceRequestDto(2,
//                "Tom",
//                "Smith",
//                "(000) 000-0000",
//                "tomsmith@gmail.com",
//                "TCU",
//                "game day",
//                "TCU",
//                "2850 Stadium Drive, Fort Worth TX 76109",
//                "yes",
//                "N/A",
//                "N/A",
//                "N/A",
//                "football game",
//                RequestStatus.PENDING,
//                "bring flags");
//        String json = this.objectMapper.writeValueAsString(superFrogAppearanceRequestDto);
//
//        SuperFrogAppearanceRequest updatedAppearanceRequest = new SuperFrogAppearanceRequest();
//        updatedAppearanceRequest.setRequestId(2);
//        updatedAppearanceRequest.setContactFirstName("Tom");
//        updatedAppearanceRequest.setContactLastName("Smith");
//        updatedAppearanceRequest.setPhoneNumber("(000) 000-0000");
//        updatedAppearanceRequest.setEmail("tomsmith@gmail.com");
//        updatedAppearanceRequest.setEventType("TCU");
//        updatedAppearanceRequest.setEventTitle("game day");
//        updatedAppearanceRequest.setNameOfOrg("TCU");
//        updatedAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
//        updatedAppearanceRequest.setIsOnTCUCampus("yes");
//        updatedAppearanceRequest.setSpecialInstructions("N/A");
//        updatedAppearanceRequest.setExpenses("N/A");
//        updatedAppearanceRequest.setOutsideOrgs("N/A");
//        updatedAppearanceRequest.setDescription("football game");
//        updatedAppearanceRequest.setStatus(RequestStatus.PENDING);
//
//        given(this.superFrogAppearanceRequestService.update(eq(2), any(SuperFrogAppearanceRequest.class)))
//                .willReturn(updatedAppearanceRequest);
//
//        // When and then
//        this.mockMvc
//                .perform(put("/api/appearances/2").contentType(MediaType.APPLICATION_JSON)
//                        .content(json).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Update Success"))
//                .andExpect(jsonPath("$.data.requestId").isNotEmpty())
//                .andExpect(jsonPath("$.data.contactFirstName").value(updatedAppearanceRequest.getContactFirstName()))
//                .andExpect(jsonPath("$.data.contactLastName").value(updatedAppearanceRequest.getContactLastName()))
//                .andExpect(jsonPath("$.data.phoneNumber").value(updatedAppearanceRequest.getPhoneNumber()))
//                .andExpect(jsonPath("$.data.email").value(updatedAppearanceRequest.getEmail()))
//                .andExpect(jsonPath("$.data.eventType").value(updatedAppearanceRequest.getEventType().toString()))
//                .andExpect(jsonPath("$.data.eventTitle").value(updatedAppearanceRequest.getEventTitle()))
//                .andExpect(jsonPath("$.data.nameOfOrg").value(updatedAppearanceRequest.getNameOfOrg()))
//                .andExpect(jsonPath("$.data.address").value(updatedAppearanceRequest.getAddress()))
//                .andExpect(jsonPath("$.data.isOnTCUCampus").value(updatedAppearanceRequest.getIsOnTCUCampus()))
//                .andExpect(
//                        jsonPath("$.data.specialInstructions").value(updatedAppearanceRequest.getSpecialInstructions()))
//                .andExpect(jsonPath("$.data.expenses").value(updatedAppearanceRequest.getExpenses()))
//                .andExpect(jsonPath("$.data.outsideOrgs").value(updatedAppearanceRequest.getOutsideOrgs()))
//                .andExpect(jsonPath("$.data.description").value(updatedAppearanceRequest.getDescription()))
//                .andExpect(jsonPath("$.data.status").value(updatedAppearanceRequest.getStatus().toString()));
//    }
//
//    @Test
//    void testUpdateAppearanceRequestWithNonExistentId() throws Exception {
//        // Given
//        SuperFrogAppearanceRequestDto appearanceRequestDto = new SuperFrogAppearanceRequestDto(123456,
//                "First",
//                "Last",
//                "(333) 333-3333",
//                "email@gmail.com",
//                "TCU",
//                "event title",
//                "name of org",
//                "2850 Stadium Drive, Fort Worth TX 76109",
//                "yes",
//                "N/A",
//                "N/A",
//                "N/A",
//                "description",
//                RequestStatus.PENDING,
//                "bring flags");
//        String json = this.objectMapper.writeValueAsString(appearanceRequestDto);
//
//        given(this.superFrogAppearanceRequestService.update(eq(123456), any(SuperFrogAppearanceRequest.class)))
//                .willThrow(new ObjectNotFoundException("superfrogappearancerequest", 123456));
//
//        // When and then
//        this.mockMvc
//                .perform(put("/api/appearances/123456").contentType(MediaType.APPLICATION_JSON)
//                        .content(json).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(false))
//                .andExpect(jsonPath("$.code").value(HttpStatusCode.NOT_FOUND))
//                .andExpect(jsonPath("$.message").value("Could not find superfrogappearancerequest with Id 123456 :("))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }
//
//    @Test
//    void testDeleteAppearanceRequestSuccess() throws Exception {
//        // Given
//        doNothing().when(this.superFrogAppearanceRequestService).delete(123456);
//
//        // When and then
//        this.mockMvc.perform(delete("/api/appearances/123456").accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Delete Success"))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }
//
//    @Test
//    void testDeleteAppearanceRequestWithNonExistentId() throws Exception {
//        // Given
//        doThrow(new ObjectNotFoundException("superfrogappearancerequest", 777)).when(this.superFrogAppearanceRequestService)
//                .delete(777);
//
//        // When and then
//        this.mockMvc.perform(delete("/api/appearances/777").accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(false))
//                .andExpect(jsonPath("$.code").value(HttpStatusCode.NOT_FOUND))
//                .andExpect(jsonPath("$.message").value("Could not find superfrogappearancerequest with Id 777 :("))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }

    // Use Case 4: Approve an Appearance Request
    @Test
    void testApproveAppearanceRequestSuccess() throws Exception {
        // Given
        int requestId = 2;
        SuperFrogAppearanceRequest approvedRequest = this.superFrogAppearanceRequests.get(1);
        approvedRequest.setStatus(RequestStatus.APPROVED);
        given(this.superFrogAppearanceRequestService.approveRequest(requestId)).willReturn(approvedRequest);

        // When & Then
        this.mockMvc.perform(post("/api/appearances/requests/" + requestId + "/approve")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Request Approved"))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));
    }

    // Use Case 4: Reject an Appearance Request
    @Test
    void testRejectAppearanceRequestSuccess() throws Exception {
        // Given
        int requestId = 3;
        String rejectionReason = "Unavailable on requested date";
        SuperFrogAppearanceRequest rejectedRequest = this.superFrogAppearanceRequests.get(2);
        rejectedRequest.setStatus(RequestStatus.REJECTED);
        rejectedRequest.setReason(rejectionReason);
        given(this.superFrogAppearanceRequestService.rejectRequest(requestId, rejectionReason)).willReturn(rejectedRequest);

        // When & Then
        this.mockMvc.perform(post("/api/appearances/requests/" + requestId + "/reject")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(rejectionReason))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Request Rejected"))
                .andExpect(jsonPath("$.data.status").value("REJECTED"))
                .andExpect(jsonPath("$.data.reason").value(rejectionReason));
    }

    // Use Case 6: Search for Appearance Requests with Results
    @Test
    public void testSearchAppearanceRequestsReturnsResults() throws Exception {
        // Given
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setEventTitle("Test Event");
        given(service.search(any())).willReturn(Collections.singletonList(request));

        // When & Then
        mockMvc.perform(post("/api/appearances/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventTitle\":\"Test Event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].eventTitle").value("Test Event"));
    }

    // Use Case 6: Search for Appearance Requests with No Results
    @Test
    public void testSearchAppearanceRequestsReturnsNoResults() throws Exception {
        // Given
        given(service.search(any())).willReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(post("/api/appearances/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventTitle\":\"Unknown Event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    // Use Case 7: Test for successful retrieval of an appearance request
    @Test
    public void getAppearanceRequestById_Success() throws Exception {
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        // populate other necessary fields...

        Mockito.when(service.findById(1)).thenReturn(request);

        mockMvc.perform(get("/api/appearances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(request.getRequestId()));
    }

    // Use Case 7: Test for request not found scenario
    @Test
    public void getAppearanceRequestById_NotFound() throws Exception {
        Mockito.when(service.findById(1)).thenThrow(new ObjectNotFoundException("SuperFrogAppearanceRequest", 1));

        mockMvc.perform(get("/api/appearances/1"))
                .andExpect(status().isNotFound());
    }


}
