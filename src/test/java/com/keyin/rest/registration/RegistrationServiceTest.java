package com.keyin.rest.registration;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventRepository;
import com.keyin.rest.user.User;
import com.keyin.rest.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_validUserAndEvent_returnsSavedRegistration() {

        User user = new User(1L, "Test User", "test@example.com");

        Event event = new Event();
        event.setId(2L);
        event.setTitle("Test Event");

        Registration registration = new Registration(user, event, null);

        Registration savedRegistration = new Registration(user, event, LocalDateTime.now());
        savedRegistration.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(2L)).thenReturn(Optional.of(event));
        when(registrationRepository.save(any(Registration.class))).thenReturn(savedRegistration);

        Registration result = registrationService.create(registration);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getEvent()).isEqualTo(event);
        assertThat(result.getRegistrationDate()).isNotNull();

        verify(userRepository).findById(1L);
        verify(eventRepository).findById(2L);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void create_userNotFound_throwsException() {
        User user = new User(999L, "Non-existent", "none@example.com");
        Event event = new Event();
        event.setId(2L);

        Registration registration = new Registration(user, event, null);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.create(registration))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found with id 999");

        verify(userRepository).findById(999L);
        verify(eventRepository, never()).findById(any());
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void create_eventNotFound_throwsException() {
        User user = new User(1L, "Test User", "test@example.com");
        Event event = new Event();
        event.setId(999L);

        Registration registration = new Registration(user, event, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.create(registration))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Event not found with id 999");

        verify(userRepository).findById(1L);
        verify(eventRepository).findById(999L);
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void getAll_returnsList() {
        Registration reg1 = new Registration();
        reg1.setId(1L);
        Registration reg2 = new Registration();
        reg2.setId(2L);

        when(registrationRepository.findAll()).thenReturn(Arrays.asList(reg1, reg2));

        List<Registration> result = registrationService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        verify(registrationRepository).findAll();
    }

    @Test
    void getAll_emptyList_returnsEmptyList() {
        when(registrationRepository.findAll()).thenReturn(Collections.emptyList());

        List<Registration> result = registrationService.getAll();

        assertThat(result).isEmpty();
        verify(registrationRepository).findAll();
    }

    @Test
    void getById_found_returnsRegistration() {
        Registration registration = new Registration();
        registration.setId(5L);
        when(registrationRepository.findById(5L)).thenReturn(Optional.of(registration));

        Registration result = registrationService.getById(5L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
        verify(registrationRepository).findById(5L);
    }

    @Test
    void getById_notFound_throws404() {
        when(registrationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.getById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Registration not found with id 99");

        verify(registrationRepository).findById(99L);
    }

    @Test
    void update_existingRegistration_returnsUpdated() {
        Registration existing = new Registration();
        existing.setId(5L);

        User user = new User(1L, "Updated User", "updated@example.com");
        Event event = new Event();
        event.setId(2L);
        event.setTitle("Updated Event");

        Registration updateRequest = new Registration();
        updateRequest.setUser(user);
        updateRequest.setEvent(event);

        Registration savedRegistration = new Registration(user, event, LocalDateTime.now());
        savedRegistration.setId(5L);

        when(registrationRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(2L)).thenReturn(Optional.of(event));
        when(registrationRepository.save(any(Registration.class))).thenReturn(savedRegistration);

        Registration result = registrationService.update(5L, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getEvent()).isEqualTo(event);
        assertThat(result.getRegistrationDate()).isNotNull();

        verify(registrationRepository).findById(5L);
        verify(userRepository).findById(1L);
        verify(eventRepository).findById(2L);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void update_registrationNotFound_throwsException() {
        User user = new User(1L, "User", "user@example.com");
        Event event = new Event();
        event.setId(2L);

        Registration updateRequest = new Registration();
        updateRequest.setUser(user);
        updateRequest.setEvent(event);

        when(registrationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.update(999L, updateRequest))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Registration not found with id 999");

        verify(registrationRepository).findById(999L);
        verify(userRepository, never()).findById(any());
        verify(eventRepository, never()).findById(any());
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void update_userNotFound_throwsException() {
        Registration existing = new Registration();
        existing.setId(5L);

        User user = new User(999L, "Non-existent", "none@example.com");
        Event event = new Event();
        event.setId(2L);

        Registration updateRequest = new Registration();
        updateRequest.setUser(user);
        updateRequest.setEvent(event);

        when(registrationRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.update(5L, updateRequest))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found with id 999");

        verify(registrationRepository).findById(5L);
        verify(userRepository).findById(999L);
        verify(eventRepository, never()).findById(any());
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void update_eventNotFound_throwsException() {
        Registration existing = new Registration();
        existing.setId(5L);

        User user = new User(1L, "User", "user@example.com");
        Event event = new Event();
        event.setId(999L);

        Registration updateRequest = new Registration();
        updateRequest.setUser(user);
        updateRequest.setEvent(event);

        when(registrationRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.update(5L, updateRequest))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Event not found with id 999");

        verify(registrationRepository).findById(5L);
        verify(userRepository).findById(1L);
        verify(eventRepository).findById(999L);
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void deleteById_callsRepository() {
        registrationService.deleteById(7L);
        verify(registrationRepository).deleteById(7L);
    }

    @Test
    void deleteById_nonExistent_stillCallsRepository() {
        // deleteById doesn't throw exception even if ID doesn't exist
        registrationService.deleteById(999L);
        verify(registrationRepository).deleteById(999L);
    }
}
