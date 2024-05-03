package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void testReverseDecisionApprovedToRejected() {
        // Given
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.APPROVED);

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(superFrogAppearanceRequestRepository.save(request)).thenReturn(request);

        // When
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.reverseDecision(1);

        // Then
        assertEquals(RequestStatus.REJECTED, result.getStatus());
        verify(superFrogAppearanceRequestRepository).save(request);
        verify(superFrogAppearanceRequestRepository).findById(1);
    }

    @Test
    void testReverseDecisionRejectedToApproved() {
        // Given
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.REJECTED);

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(superFrogAppearanceRequestRepository.save(request)).thenReturn(request);

        // When
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.reverseDecision(1);

        // Then
        assertEquals(RequestStatus.APPROVED, result.getStatus());
        verify(superFrogAppearanceRequestRepository).save(request);
        verify(superFrogAppearanceRequestRepository).findById(1);
    }

    @Test
    void testReverseDecisionNotFound() {
        // Given
        when(superFrogAppearanceRequestRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.reverseDecision(99));
        verify(superFrogAppearanceRequestRepository, never()).save(any(SuperFrogAppearanceRequest.class));
        verify(superFrogAppearanceRequestRepository).findById(99);
    }

    @Test
    void testMarkCompleteSuccess(){
        //Given
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.APPROVED);
        request.setEndTime(LocalTime.now().minusMinutes(30));

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(superFrogAppearanceRequestRepository.save(request)).thenReturn(request);

        // When
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.setComplete(1);

        // Then
        assertEquals(RequestStatus.COMPLETED, result.getStatus());
        verify(superFrogAppearanceRequestRepository).save(request);
        verify(superFrogAppearanceRequestRepository).findById(1);
    }

    @Test
    void testMarkCompleteNotFound(){
        // Given
        when(superFrogAppearanceRequestRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.setComplete(99));
        verify(superFrogAppearanceRequestRepository, never()).save(any(SuperFrogAppearanceRequest.class));
        verify(superFrogAppearanceRequestRepository).findById(99);
    }

    @Test
    void testMarkIncompleteSuccess(){
        //Given
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.APPROVED);
        request.setEndTime(LocalTime.now().minusMinutes(30));

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(superFrogAppearanceRequestRepository.save(request)).thenReturn(request);

        // When
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.setIncomplete(1);

        // Then
        assertEquals(RequestStatus.INCOMPLETE, result.getStatus());
        verify(superFrogAppearanceRequestRepository).save(request);
        verify(superFrogAppearanceRequestRepository).findById(1);
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
}
