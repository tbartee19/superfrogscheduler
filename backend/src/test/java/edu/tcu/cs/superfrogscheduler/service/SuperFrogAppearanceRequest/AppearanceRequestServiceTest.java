package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.system.IdWorker;
import edu.tcu.cs.superfrogscheduler.system.NotificationService;
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
import org.springframework.beans.factory.annotation.Autowired;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppearanceRequestServiceTest {
    
    @Mock
    SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

     @Mock
     IdWorker idWorker;

    @InjectMocks
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    @Mock
    SuperFrogStudentRepository superfrogStudentRepository;
    @Mock
    private NotificationService notificationService;

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
        sfar1.setStatus(RequestStatus.PENDING);
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
        sfar2.setStatus(RequestStatus.APPROVED);
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
        sfar3.setStatus(RequestStatus.REJECTED);
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
    void testUpdateSuccess() {
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
        oldAppearanceRequest.setEventDate(LocalDate.now()); // Ensure event date is set
        oldAppearanceRequest.setStartTime(LocalTime.of(10, 0)); // Set start time
        oldAppearanceRequest.setEndTime(LocalTime.of(11, 0)); // Set end time

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
        update.setEventDate(LocalDate.now()); // Ensure event date is set
        update.setStartTime(LocalTime.of(10, 0)); // Set start time
        update.setEndTime(LocalTime.of(11, 0)); // Set end time

        given(this.superFrogAppearanceRequestRepository.findById(123456)).willReturn(Optional.of(oldAppearanceRequest));
        given(this.superFrogAppearanceRequestRepository.save(oldAppearanceRequest)).willReturn(oldAppearanceRequest);

        // When
        SuperFrogAppearanceRequest updatedRequest = this.superFrogAppearanceRequestService.update(123456, update);

        // Then
        assertThat(updatedRequest.getRequestId()).isEqualTo(123456);
        assertThat(updatedRequest.getDescription()).isEqualTo(update.getDescription());
        assertThat(updatedRequest.getStartTime()).isEqualTo(update.getStartTime()); // Verify start time is updated
        assertThat(updatedRequest.getEndTime()).isEqualTo(update.getEndTime()); // Verify end time is updated
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

    // UC-4: Approve an appearance request
    @Test
    void approveRequest_Success() {
        System.out.println("Running approveRequest_Success");
        Integer requestId = 1;
        SuperFrogAppearanceRequest request = superFrogAppearanceRequests.get(0);
        request.setStatus(RequestStatus.PENDING); // Ensure it's pending before approval

        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(request));
        given(superFrogAppearanceRequestRepository.save(request)).willReturn(request);

        SuperFrogAppearanceRequest approvedRequest = superFrogAppearanceRequestService.approveRequest(requestId);

        assertEquals(RequestStatus.APPROVED, approvedRequest.getStatus());
        verify(superFrogAppearanceRequestRepository).save(request);
    }

    // UC-4: Handle not found when approving a request
    @Test
    void approveRequest_NotFound() {
        System.out.println("Running approveRequest_NotFound");
        Integer requestId = 999;
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            superFrogAppearanceRequestService.approveRequest(requestId);
        });

        assertEquals("Could not find SuperFrogAppearanceRequest with Id 999 :(", exception.getMessage());
    }

    // UC-4: Reject an appearance request successfully
    @Test
    void rejectRequest_Success() {
        System.out.println("Running rejectRequest_Success");
        Integer requestId = 1;
        String reason = "Insufficient resources";
        SuperFrogAppearanceRequest request = superFrogAppearanceRequests.get(0);
        request.setStatus(RequestStatus.PENDING);

        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(request));
        given(superFrogAppearanceRequestRepository.save(request)).willReturn(request);

        SuperFrogAppearanceRequest rejectedRequest = superFrogAppearanceRequestService.rejectRequest(requestId, reason);

        assertEquals(RequestStatus.REJECTED, rejectedRequest.getStatus());
        assertEquals(reason, rejectedRequest.getRejectionReason());
        verify(superFrogAppearanceRequestRepository).save(request);
    }

    // UC-6: Search by event title and find requests
    @Test
    void SearchByEventTitle_thenRequestsFound() {
        System.out.println("Running SearchByEventTitle_thenRequestsFound");
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("eventTitle", "wedding");

        given(superFrogAppearanceRequestRepository.findByCriteria(criteria))
                .willReturn(superFrogAppearanceRequests.stream()
                        .filter(r -> "wedding".equals(r.getEventTitle()))
                        .collect(Collectors.toList()));

        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.searchRequests(criteria);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getEventTitle()).isEqualTo("wedding");
        verify(superFrogAppearanceRequestRepository, times(1)).findByCriteria(criteria);
    }

    // UC-6: Search by request status and find requests
    @Test
    void searchByRequestStatus_thenRequestsFound() {
        System.out.println("Running searchByRequestStatus_thenRequestsFound");
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("status", RequestStatus.PENDING);

        given(superFrogAppearanceRequestRepository.findByCriteria(criteria))
                .willReturn(superFrogAppearanceRequests.stream()
                        .filter(r -> r.getStatus() == RequestStatus.PENDING)
                        .collect(Collectors.toList()));

        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.searchRequests(criteria);

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getStatus()).isEqualTo(RequestStatus.PENDING);
        verify(superFrogAppearanceRequestRepository, times(1)).findByCriteria(criteria);
    }

    @Test
    void viewRequestDetails_Success() {
        // Given
        Integer requestId = 1;
        SuperFrogAppearanceRequest expectedRequest = superFrogAppearanceRequests.get(0);

        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(expectedRequest));

        // When
        SuperFrogAppearanceRequest actualRequest = superFrogAppearanceRequestService.findRequestById(requestId);

        // Then
        assertEquals(expectedRequest, actualRequest);
        System.out.println("Request Details: " + actualRequest);
    }

    @Test
    void viewRequestDetails_NotFound() {
        // Given
        Integer requestId = 999;
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.findRequestById(requestId));
    }
    

    @Test
    void updateRequestDetails_ValidationFailure() {
        // Given
        Integer requestId = 1;
        SuperFrogAppearanceRequest existingRequest = superFrogAppearanceRequests.get(0);
        SuperFrogAppearanceRequest updateDetails = new SuperFrogAppearanceRequest();

        // Setting updated details with invalid data
        updateDetails.setContactFirstName(""); // Invalid as it's required
        updateDetails.setPhoneNumber("invalid phone number");

        // Mocking repository behavior
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.of(existingRequest));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> superFrogAppearanceRequestService.update(requestId, updateDetails),
                "Expected validateUpdateDetails to throw, but it did not");
    }

    @Test
    void updateRequestDetails_NotFound() {
        // Given
        Integer requestId = 999; // Non-existent request ID
        SuperFrogAppearanceRequest updateDetails = new SuperFrogAppearanceRequest();

        // Mocking repository behavior for not found scenario
        given(superFrogAppearanceRequestRepository.findById(requestId)).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.update(requestId, updateDetails),
                "Expected update to throw ObjectNotFoundException, but it did not");
    }

    @Test
    void assignStudentToApprovedRequest_Success() {
        // Given
        Integer requestId = 2; // ID of an approved request
        Integer studentId = 1;
        SuperFrogAppearanceRequest approvedRequest = superFrogAppearanceRequests.get(1); // the approved request from setUp
        SuperFrogStudent assignedStudent = new SuperFrogStudent();
        assignedStudent.setId("1");
        assignedStudent.setFirstName("Super");
        assignedStudent.setLastName("Frog");

        when(superFrogAppearanceRequestRepository.findById(requestId)).thenReturn(Optional.of(approvedRequest));
        when(superfrogStudentRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(assignedStudent));

        // Act
        superFrogAppearanceRequestService.assignSuperFrogStudent(requestId, studentId);

        // Assert
        assertEquals(RequestStatus.ASSIGNED, approvedRequest.getStatus());
        assertSame(assignedStudent, approvedRequest.getSuperfrogStudent());
        verify(superFrogAppearanceRequestRepository).save(approvedRequest);
        verify(notificationService).sendNotification("The appearance request has been assigned to: " + assignedStudent.getFirstName() + " " + assignedStudent.getLastName());
        verify(notificationService).sendNotification("You have been assigned to an appearance request on: " + approvedRequest.getEventDate().toString());
    }

    @Test
    void assignStudentToUnapprovedRequest_Failure() {
        // Given
        Integer requestId = 1; // Assume this is a non-approved request ID
        Integer studentId = 1; // Student ID to assign
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(requestId);
        request.setStatus(RequestStatus.PENDING); // This status should trigger the exception

        SuperFrogStudent student = new SuperFrogStudent();
        student.setId(studentId.toString());

        when(superFrogAppearanceRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(superfrogStudentRepository.findById(studentId.toString())).thenReturn(Optional.of(student));

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            superFrogAppearanceRequestService.assignSuperFrogStudent(requestId, studentId);
        });

        String expectedMessage = "Could not find Cannot assign a student to an unapproved request. with Id " + requestId + " :(";
        assertEquals(expectedMessage, exception.getMessage());
    }






    @Test
    void assignStudentToRequest_RequestNotFound() {
        // Given
        Integer requestId = 999; // Non-existent request ID
        Integer studentId = 1;

        when(superFrogAppearanceRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.assignSuperFrogStudent(requestId, studentId));
    }




}

