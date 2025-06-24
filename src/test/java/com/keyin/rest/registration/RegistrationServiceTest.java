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
    void create_returnsSavedRegistration() {
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        event.setId(2L);
        Registration registration = new Registration(user, event, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(2L)).thenReturn(Optional.of(event));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationService.create(registration);

        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getEvent()).isEqualTo(event);
        assertThat(result.getRegistrationDate()).isNotNull();
    }

    @Test
    void getAll_returnsList() {
        when(registrationRepository.findAll()).thenReturn(Collections.singletonList(new Registration()));
        List<Registration> result = registrationService.getAll();
        assertThat(result).hasSize(1);
        verify(registrationRepository).findAll();
    }

    @Test
    void getById_found_returnsRegistration() {
        Registration registration = new Registration();
        registration.setId(5L);
        when(registrationRepository.findById(5L)).thenReturn(Optional.of(registration));

        Registration result = registrationService.getById(5L);
        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void getById_notFound_throws404() {
        when(registrationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.getById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Registration not found with id 99");
    }

    @Test
    void deleteById_callsRepository() {
        registrationService.deleteById(7L);
        verify(registrationRepository).deleteById(7L);
    }
}
