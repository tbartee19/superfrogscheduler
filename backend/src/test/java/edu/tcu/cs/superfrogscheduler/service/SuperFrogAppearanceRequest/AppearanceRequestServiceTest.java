package edu.tcu.cs.superfrogscheduler.service.SuperFrogAppearanceRequest;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import edu.tcu.cs.superfrogscheduler.model.converter.SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.model.dto.SuperFrogAppearanceRequestDto;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AppearanceRequestServiceTest {

    @Mock
    SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    SuperFrogAppearanceRequestService superFrogAppearanceRequestService;

    List<SuperFrogAppearanceRequest> superFrogAppearanceRequests;

    @MockBean
    private SuperFrogAppearanceRequestToSuperFrogAppearanceRequestDtoConverter converter;



    private List<SuperFrogAppearanceRequest> allRequests;
    @BeforeEach
    void setUp() {
        superFrogAppearanceRequests = new ArrayList<>();

        // Create sample data for testing
        SuperFrogAppearanceRequest sfar1 = new SuperFrogAppearanceRequest();
        sfar1.setRequestId(1);
        sfar1.setEventDate(LocalDate.of(2024, 5, 10));
        sfar1.setStartTime(LocalTime.of(11, 0));
        sfar1.setEndTime(LocalTime.of(12, 0));
        sfar1.setContactFirstName("John");
        sfar1.setContactLastName("Doe");
        sfar1.setPhoneNumber("(123) 456-7890");
        sfar1.setEmail("johndoe@gmail.com");
        sfar1.setEventType("PRIVATE");
        sfar1.setEventTitle("Wedding");
        sfar1.setNameOfOrg("Wedding Company");
        sfar1.setAddress("200 Texas Street, Fort Worth TX 76102");
        sfar1.setSpecialInstructions("N/A");
        sfar1.setExpenses("N/A");
        sfar1.setOutsideOrgs("N/A");
        sfar1.setDescription("Afternoon wedding");
        superFrogAppearanceRequests.add(sfar1);

        SuperFrogAppearanceRequest sfar2 = new SuperFrogAppearanceRequest();
        sfar2.setRequestId(2);
        sfar2.setEventDate(LocalDate.of(2024, 6, 10));
        sfar2.setStartTime(LocalTime.of(12, 0));
        sfar2.setEndTime(LocalTime.of(13, 0));
        sfar2.setContactFirstName("Tom");
        sfar2.setContactLastName("Smith");
        sfar2.setPhoneNumber("(999) 999-9999");
        sfar2.setEmail("tomsmith@gmail.com");
        sfar2.setEventType("TCU");
        sfar2.setEventTitle("Game Day");
        sfar2.setNameOfOrg("TCU");
        sfar2.setAddress("2850 Stadium Drive, Fort Worth TX 76109");
        sfar2.setSpecialInstructions("N/A");
        sfar2.setExpenses("N/A");
        sfar2.setOutsideOrgs("N/A");
        sfar2.setDescription("Football game");
        superFrogAppearanceRequests.add(sfar2);

        SuperFrogAppearanceRequest sfar3 = new SuperFrogAppearanceRequest();
        sfar3.setRequestId(3);
        sfar3.setEventDate(LocalDate.of(2024, 7, 10));
        sfar3.setStartTime(LocalTime.of(14, 0));
        sfar3.setEndTime(LocalTime.of(15, 0));
        sfar3.setContactFirstName("Fred");
        sfar3.setContactLastName("Johnson");
        sfar3.setPhoneNumber("(888) 888-8888");
        sfar3.setEmail("fredjohnson@gmail.com");
        sfar3.setEventType("PUBLIC");
        sfar3.setEventTitle("School Visit");
        sfar3.setNameOfOrg("Alice E. Carlson Elementary School");
        sfar3.setAddress("3320 W Cantey St, Fort Worth TX 76109");
        sfar3.setSpecialInstructions("N/A");
        sfar3.setExpenses("N/A");
        sfar3.setOutsideOrgs("N/A");
        sfar3.setDescription("School assembly");
        superFrogAppearanceRequests.add(sfar3);

        // Mock the repository to return the prepared list
        when(superFrogAppearanceRequestRepository.findAll()).thenReturn(superFrogAppearanceRequests);
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

    @Test
    void testApproveRequest() {
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.PENDING);

        given(superFrogAppearanceRequestRepository.findById(1)).willReturn(Optional.of(request));
        given(superFrogAppearanceRequestRepository.save(request)).willReturn(request);

        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.approveRequest(1);

        assertThat(result.getStatus()).isEqualTo(RequestStatus.APPROVED);
    }

    @Test
    void testRejectRequest() {
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setRequestId(1);
        request.setStatus(RequestStatus.PENDING);

        given(superFrogAppearanceRequestRepository.findById(1)).willReturn(Optional.of(request));
        request.setRejectionReason("Not suitable for the event");
        given(superFrogAppearanceRequestRepository.save(request)).willReturn(request);

        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.rejectRequest(1, "Not suitable for the event");

        assertThat(result.getStatus()).isEqualTo(RequestStatus.REJECTED);
        assertThat(result.getRejectionReason()).isEqualTo("Not suitable for the event");
    }

    @Test
    void testSearchByEventTitle() {
        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.searchAppearanceRequests(null, null, "Game Day", null, null, null, null);
        assertEquals(1, results.size(), "Should find one request with the title 'Game Day'");
    }

    @Test
    void testSearchByContactFirstName() {
        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.searchAppearanceRequests(null, null, null, "Tom", null, null, null);
        assertEquals(1, results.size(), "Should find one request with the contact first name 'Tom'");
    }

    @Test
    void testNoResults() {
        List<SuperFrogAppearanceRequest> results = superFrogAppearanceRequestService.searchAppearanceRequests(null, null, "Nonexistent Event", null, null, null, null);
        assertEquals(0, results.size(), "Should find no requests with a non-existent title");
    }

    @Test
    public void testFindByIdWhenRequestExists() {
        // Arrange
        SuperFrogAppearanceRequest expectedRequest = new SuperFrogAppearanceRequest();
        expectedRequest.setRequestId(1);
        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(expectedRequest));

        // Act
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedRequest.getRequestId(), result.getRequestId());
        verify(superFrogAppearanceRequestRepository).findById(1);  // Verify this method is indeed called
    }

    @Test
    public void testFindByIdWhenRequestDoesNotExist() {
        // Arrange
        // This specific setup only for this test if different from global setup
        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.findById(1));
        verify(superFrogAppearanceRequestRepository).findById(1);  // Ensure this method is called
    }


    @Test
    @Transactional
    public void testCreateFindDelete() {
        // Create an appearance request
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setContactFirstName("Test");
        request.setContactLastName("User");
        request.setPhoneNumber("(123) 456-7890");
        request.setEmail("testuser@example.com");
        request.setEventType("PRIVATE");
        request.setEventTitle("Birthday Party");
        request.setNameOfOrg("Private Person");
        request.setAddress("1234 Test St, Testing TX");
        request.setEventDate(LocalDate.now());
        request.setStartTime(LocalTime.of(14, 0));
        request.setEndTime(LocalTime.of(15, 0));
        request.setDescription("A private birthday party.");
        request.setStatus(RequestStatus.PENDING);

        // Save the request
        SuperFrogAppearanceRequest savedRequest = superFrogAppearanceRequestService.save(request);
        assertNotNull(savedRequest.getRequestId());

        // Verify it can be found
        SuperFrogAppearanceRequest foundRequest = superFrogAppearanceRequestService.findById(savedRequest.getRequestId());
        assertEquals("Test", foundRequest.getContactFirstName());

        // Delete the request
        superFrogAppearanceRequestService.delete(savedRequest.getRequestId());

        // Verify it's no longer there
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.findById(savedRequest.getRequestId()));
        assertEquals("Could not find request with id: " + savedRequest.getRequestId(), exception.getMessage());
    }

    @Test
    public void testSaveAndFind() {
        SuperFrogAppearanceRequest request = new SuperFrogAppearanceRequest();
        request.setContactFirstName("Test");
        request.setEmail("test@example.com");

        when(superFrogAppearanceRequestRepository.save(any())).thenAnswer(i -> {
            SuperFrogAppearanceRequest saved = i.getArgument(0);
            saved.setRequestId(1);  // Simulate setting ID on save
            return saved;
        });
        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(request));

        SuperFrogAppearanceRequest saved = superFrogAppearanceRequestService.save(request);
        assertNotNull(saved.getRequestId());

        Optional<SuperFrogAppearanceRequest> found = superFrogAppearanceRequestRepository.findById(saved.getRequestId());
        assertTrue(found.isPresent());
    }

    private SuperFrogAppearanceRequest existingRequest;

    @Test
    void testEditAppearanceRequest_Successful() {
        // Arrange
        SuperFrogAppearanceRequest updatedRequest = new SuperFrogAppearanceRequest();
        updatedRequest.setEventTitle("Updated Title");
        updatedRequest.setDescription("Updated Description");

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(existingRequest));
        when(superFrogAppearanceRequestRepository.save(any(SuperFrogAppearanceRequest.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        SuperFrogAppearanceRequest result = superFrogAppearanceRequestService.update(1, updatedRequest);

        // Assert
        assertEquals("Updated Title", result.getEventTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(superFrogAppearanceRequestRepository).save(existingRequest);
        verify(superFrogAppearanceRequestRepository, times(1)).findById(1);
    }

    @Test
    void testEditAppearanceRequest_NotFound() {
        // Arrange
        when(superFrogAppearanceRequestRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> superFrogAppearanceRequestService.update(999, new SuperFrogAppearanceRequest()));

        assertTrue(exception.getMessage().contains("Could not find request with id: 999"));
        verify(superFrogAppearanceRequestRepository, never()).save(any(SuperFrogAppearanceRequest.class));
    }

    @Test
    void testEditAppearanceRequest_NoChangeOnInvalidUpdate() {
        // Assume setting an invalid event date is a part of business rules
        SuperFrogAppearanceRequest invalidUpdate = new SuperFrogAppearanceRequest();
        invalidUpdate.setEventDate(LocalDate.of(2000, 1, 1)); // Invalid past date

        when(superFrogAppearanceRequestRepository.findById(1)).thenReturn(Optional.of(existingRequest));

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> superFrogAppearanceRequestService.update(1, invalidUpdate));

        // Assert
        assertNotEquals(LocalDate.of(2000, 1, 1), existingRequest.getEventDate());
        verify(superFrogAppearanceRequestRepository, never()).save(any(SuperFrogAppearanceRequest.class));
    }

}



