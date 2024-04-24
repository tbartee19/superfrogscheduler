package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import java.util.Collections;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import edu.tcu.cs.superfrogscheduler.model.SearchCriteria;
import edu.tcu.cs.superfrogscheduler.system.IdWorker;
import edu.tcu.cs.superfrogscheduler.system.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceRequestService;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppearanceRequestServiceTest {
    
    @Mock
    SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

     @Mock
     IdWorker idWorker;

    @InjectMocks
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    List<SuperFrogAppearanceRequest> superFrogAppearanceRequests;

    @BeforeEach
    void setUp(){
        this.superFrogAppearanceRequests = new ArrayList<>();

        SuperFrogAppearanceRequest sfar1 = new SuperFrogAppearanceRequest();
        sfar1.setRequestId(1);
        sfar1.setEventDate(LocalDate.of(2024, 5, 10));
        sfar1.setStartTime(LocalTime.of(11, 0, 0));
        sfar1.setEndTime(LocalTime.of(12, 0, 0));
        sfar1.setContactFirstName("John");
        sfar1.setContactLastName("Doe");
        sfar1.setPhoneNumber("(123) 456-7890");
        sfar1.setEmail("johndoe@gmail.com");
        sfar1.setEventType("PRIVATE");
        sfar1.setEventTitle("wedding");
        sfar1.setNameOfOrg("wedding company");
        sfar1.setAddress("200 Texas Street, Fort Worth TX 76102");
        sfar1.setSpecialInstructions("N/A");
        sfar1.setExpenses("N/A");
        sfar1.setOutsideOrgs("N/A");
        sfar1.setDescription("afternoon wedding");
        this.superFrogAppearanceRequests.add(sfar1);

        SuperFrogAppearanceRequest sfar2 = new SuperFrogAppearanceRequest();
        sfar2.setRequestId(2);
        sfar2.setEventDate(LocalDate.of(2024, 6, 10));
        sfar2.setStartTime(LocalTime.of(12, 0, 0));
        sfar2.setEndTime(LocalTime.of(13, 0, 0));
        sfar2.setContactFirstName("Tom");
        sfar2.setContactLastName("Smith");
        sfar2.setPhoneNumber("(999) 999-9999");
        sfar2.setEmail("tomsmith@gmail.com");
        sfar2.setEventType("TCU");
        sfar2.setEventTitle("game day");
        sfar2.setNameOfOrg("TCU");
        sfar2.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        sfar2.setSpecialInstructions("N/A");
        sfar2.setExpenses("N/A");
        sfar2.setOutsideOrgs("N/A");
        sfar2.setDescription("football game");
        this.superFrogAppearanceRequests.add(sfar2);

        SuperFrogAppearanceRequest sfar3 = new SuperFrogAppearanceRequest();
        sfar3.setRequestId(3);
        sfar3.setEventDate(LocalDate.of(2024, 7, 10));
        sfar3.setStartTime(LocalTime.of(14, 0, 0));
        sfar3.setEndTime(LocalTime.of(15, 0, 0));
        sfar3.setContactFirstName("Fred");
        sfar3.setContactLastName("Johnson");
        sfar3.setPhoneNumber("(888) 888-888");
        sfar3.setEmail("fredjohnson@gmail.com");
        sfar3.setEventType("PUBLIC");
        sfar3.setEventTitle("school visit");
        sfar3.setNameOfOrg("Alice E. Carlson Elementary School");
        sfar3.setAddress("3320 W Cantey St, Fort Worth TX 76109");
        sfar1.setSpecialInstructions("N/A");
        sfar1.setExpenses("N/A");
        sfar1.setOutsideOrgs("N/A");
        sfar3.setDescription("school assembly");
        this.superFrogAppearanceRequests.add(sfar3);
    }

    @AfterEach
    void tearDown(){
        
    }

    @Test
    void testSaveSuccess(){
        // Given
        SuperFrogAppearanceRequest newAppearanceRequest = new SuperFrogAppearanceRequest();
        newAppearanceRequest.setContactFirstName("First");
        newAppearanceRequest.setContactLastName("Last");
        newAppearanceRequest.setPhoneNumber("(333) 333-3333");
        newAppearanceRequest.setEmail("email@gmail.com");
        newAppearanceRequest.setEventType("TCU");
        newAppearanceRequest.setEventTitle("event title");
        newAppearanceRequest.setNameOfOrg("name of org");
        newAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        newAppearanceRequest.setSpecialInstructions("N/A");
        newAppearanceRequest.setExpenses("N/A");
        newAppearanceRequest.setOutsideOrgs("N/A");
        newAppearanceRequest.setDescription("description");

        given(this.superFrogAppearanceRequestRepository.save(newAppearanceRequest)).willReturn(newAppearanceRequest);

        // When
        SuperFrogAppearanceRequest savedAppearanceRequest = this.superFrogAppearanceRequestService.save(newAppearanceRequest);

        // Then
        assertThat(savedAppearanceRequest.getRequestId()).isEqualTo(newAppearanceRequest.getRequestId());
        assertThat(savedAppearanceRequest.getContactFirstName()).isEqualTo(newAppearanceRequest.getContactFirstName());
        assertThat(savedAppearanceRequest.getContactLastName()).isEqualTo(newAppearanceRequest.getContactLastName());
        assertThat(savedAppearanceRequest.getPhoneNumber()).isEqualTo(newAppearanceRequest.getPhoneNumber());
        assertThat(savedAppearanceRequest.getEmail()).isEqualTo(newAppearanceRequest.getEmail());
        assertThat(savedAppearanceRequest.getEventType()).isEqualTo(newAppearanceRequest.getEventType());
        assertThat(savedAppearanceRequest.getEventTitle()).isEqualTo(newAppearanceRequest.getEventTitle());
        assertThat(savedAppearanceRequest.getNameOfOrg()).isEqualTo(newAppearanceRequest.getNameOfOrg());
        assertThat(savedAppearanceRequest.getAddress()).isEqualTo(newAppearanceRequest.getAddress());
        assertThat(savedAppearanceRequest.getSpecialInstructions()).isEqualTo(newAppearanceRequest.getSpecialInstructions());
        assertThat(savedAppearanceRequest.getExpenses()).isEqualTo(newAppearanceRequest.getExpenses());
        assertThat(savedAppearanceRequest.getOutsideOrgs()).isEqualTo(newAppearanceRequest.getOutsideOrgs());
        assertThat(savedAppearanceRequest.getDescription()).isEqualTo(newAppearanceRequest.getDescription());
        verify(this.superFrogAppearanceRequestRepository, times(1)).save(newAppearanceRequest);
    }

    @Test
    void testUpdateSuccess(){
        // Given
        SuperFrogAppearanceRequest oldAppearanceRequest = new SuperFrogAppearanceRequest();
        oldAppearanceRequest.setRequestId(123456);
        oldAppearanceRequest.setContactFirstName("First");
        oldAppearanceRequest.setContactLastName("Last");
        oldAppearanceRequest.setPhoneNumber("(333) 333-3333");
        oldAppearanceRequest.setEmail("email@gmail.com");
        oldAppearanceRequest.setEventType("TCU");
        oldAppearanceRequest.setEventTitle("event title");
        oldAppearanceRequest.setNameOfOrg("name of org");
        oldAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        oldAppearanceRequest.setSpecialInstructions("N/A");
        oldAppearanceRequest.setExpenses("N/A");
        oldAppearanceRequest.setOutsideOrgs("N/A");
        oldAppearanceRequest.setDescription("description");

        SuperFrogAppearanceRequest update = new SuperFrogAppearanceRequest();
        update.setContactFirstName("First");
        update.setContactLastName("Last");
        update.setPhoneNumber("(333) 333-3333");
        update.setEmail("email@gmail.com");
        update.setEventType("TCU");
        update.setEventTitle("event title");
        update.setNameOfOrg("name of org");
        update.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        update.setSpecialInstructions("N/A");
        update.setExpenses("N/A");
        update.setOutsideOrgs("N/A");
        update.setDescription("A new description");

        given(this.superFrogAppearanceRequestRepository.findById(123456)).willReturn(Optional.of(oldAppearanceRequest));
        given(this.superFrogAppearanceRequestRepository.save(oldAppearanceRequest)).willReturn(oldAppearanceRequest);

        // When
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.update(123456, update);

        // Then
        assertThat(updatedRequest.getRequestId()).isEqualTo(123456);
        assertThat(updatedRequest.getDescription()).isEqualTo(update.getDescription());
        verify(this.superFrogAppearanceRequestRepository, times(1)).findById(123456);
        verify(this.superFrogAppearanceRequestRepository, times(1)).save(oldAppearanceRequest);
    }

    @Test
    void testUpdateNotFound() {
        // Given
        SuperFrogAppearanceRequest update = new SuperFrogAppearanceRequest();
        update.setContactFirstName("First");
        update.setContactLastName("Last");
        update.setPhoneNumber("(333) 333-3333");
        update.setEmail("email@gmail.com");
        update.setEventType("TCU");
        update.setEventTitle("event title");
        update.setNameOfOrg("name of org");
        update.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        update.setSpecialInstructions("N/A");
        update.setExpenses("N/A");
        update.setOutsideOrgs("N/A");
        update.setDescription("A new description");

        given(this.superFrogAppearanceRequestRepository.findById(123456)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.superFrogAppearanceRequestService.update(123456, update);
        });

        // Then
        verify(this.superFrogAppearanceRequestRepository, times(1)).findById(123456);
    }

    @Test
    void testDeleteSuccess() {
        // Given
        SuperFrogAppearanceRequest superFrogAppearanceRequest = new SuperFrogAppearanceRequest();
        superFrogAppearanceRequest.setRequestId(123456);
        superFrogAppearanceRequest.setContactFirstName("First");
        superFrogAppearanceRequest.setContactLastName("Last");
        superFrogAppearanceRequest.setPhoneNumber("(333) 333-3333");
        superFrogAppearanceRequest.setEmail("email@gmail.com");
        superFrogAppearanceRequest.setEventType("TCU");
        superFrogAppearanceRequest.setEventTitle("event title");
        superFrogAppearanceRequest.setNameOfOrg("name of org");
        superFrogAppearanceRequest.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        superFrogAppearanceRequest.setSpecialInstructions("N/A");
        superFrogAppearanceRequest.setExpenses("N/A");
        superFrogAppearanceRequest.setOutsideOrgs("N/A");
        superFrogAppearanceRequest.setDescription("description");

        given(this.superFrogAppearanceRequestRepository.findById(123456)).willReturn(Optional.of(superFrogAppearanceRequest));
        doNothing().when(this.superFrogAppearanceRequestRepository).deleteById(123456);

        // When
        this.superFrogAppearanceRequestService.delete(123456);

        // Then
        verify(this.superFrogAppearanceRequestRepository, times(1)).deleteById(123456);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.superFrogAppearanceRequestRepository.findById(123456)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.superFrogAppearanceRequestService.delete(123456);
        });

        // Then
        verify(this.superFrogAppearanceRequestRepository, times(1)).findById(123456);
    }

    // Use Case 4: Approve an Appearance Request
    @Test
    void approveRequestSuccess() {
        // Given
        Integer requestId = 1;
        SuperFrogAppearanceRequest request = superFrogAppearanceRequests.get(0); // Assuming this is a pending request
        request.setStatus(RequestStatus.PENDING);
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(request));
        given(superFrogAppearanceRequestRepository.save(request)).willAnswer(invocation -> {
            SuperFrogAppearanceRequest req = invocation.getArgument(0);
            req.setStatus(RequestStatus.APPROVED);
            return req;
        });

        // When
        SuperFrogAppearanceRequest approvedRequest = superFrogAppearanceRequestService.approveRequest(requestId);

        // Then
        assertThat(approvedRequest.getStatus()).isEqualTo(RequestStatus.APPROVED);
        verify(superFrogAppearanceRequestRepository).save(request);
    }

    // Use Case 4: Fail to Approve an Appearance Request
    @Test
    void approveRequestNotFound() {
        // Given
        Integer requestId = 99;
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.approveRequest(requestId));
    }

    // Use Case 5: Reject an Appearance Request
    @Test
    void rejectRequestSuccess() {
        // Given
        Integer requestId = 1;
        String rejectionReason = "Insufficient resources";
        SuperFrogAppearanceRequest request = superFrogAppearanceRequests.get(0);
        request.setStatus(RequestStatus.PENDING);
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(request));
        given(superFrogAppearanceRequestRepository.save(request)).willAnswer(invocation -> {
            SuperFrogAppearanceRequest req = invocation.getArgument(0);
            req.setStatus(RequestStatus.REJECTED);
            req.setReason(rejectionReason);
            return req;
        });

        // When
        SuperFrogAppearanceRequest rejectedRequest = superFrogAppearanceRequestService.rejectRequest(requestId, rejectionReason);

        // Then
        assertThat(rejectedRequest.getStatus()).isEqualTo(RequestStatus.REJECTED);
        assertThat(rejectedRequest.getReason()).isEqualTo(rejectionReason);
        verify(superFrogAppearanceRequestRepository).save(request);
    }

    // Use Case 5: Fail to Reject an Appearance Request
    @Test
    void rejectRequestNotFound() {
        // Given
        Integer requestId = 99;
        String rejectionReason = "Unavailable resources";

        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.rejectRequest(requestId, rejectionReason));
    }



//    // Use Case 6: Search for Appearance Requests with Results (in progress)
//    @Test
//    void searchRequestsReturnsResults() {
//        // Given
//        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
//        request.setEventTitle("Test Event");
//
//        SearchCriteria criteria = new SearchCriteria();
//        criteria.setEventTitle("Test Event");
//
//        given(superFrogAppearanceRequestRepository.findAll((Sort) any())).willReturn(Collections.singletonList(request));
//
//        // When
//        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.search(criteria);
//
//        // Then
//        assertThat(results).hasSize(1);
//        assertThat(results.get(0).getEventTitle()).isEqualTo("Test Event");
//        verify(superFrogAppearanceRequestRepository).findAll((Sort) any());
//    }
//
//    // Use Case 6: Search for Appearance Requests with No Results (in progress)
//    @Test
//    void searchRequestsReturnsNoResults() {
//        // Given
//        SearchCriteria criteria = new SearchCriteria();
//        criteria.setEventTitle("Unknown Event");
//
//        given(superFrogAppearanceRequestRepository.findAll((Sort) any())).willReturn(Collections.emptyList());
//
//        // When
//        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.search(criteria);
//
//        // Then
//        assertThat(results).isEmpty();
//        verify(superFrogAppearanceRequestRepository).findAll((Sort) any());
//    }

    // Use Case 7: Test for successful retrieval
    @Test
    public void findById_Success() {
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        // populate other fields...

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));

        SuperFrogAppearanceRequest found = superFrogAppearanceRequestService.findById(1);
        assertEquals(request.getRequestId(), found.getRequestId());
    }

    // Use Case 7: Test for not found exception
    @Test
    public void findById_NotFound() {
        // set up our mock repository
        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.empty());

        // Execute the service call
        assertThrows(ObjectNotFoundException.class, () -> {
            superFrogAppearanceRequestService.findById(1);
        }, "Expected findById(1) to throw, but it didn't");
    }

}
