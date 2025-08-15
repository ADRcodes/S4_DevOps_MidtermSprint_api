package com.keyin.rest.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.rest.event.Event;
import com.keyin.rest.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable security filters for testing
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistrationService registrationService;

    private Registration sampleRegistration;
    private User sampleUser;
    private Event sampleEvent;

    @BeforeEach
    public void setUp() {
        // Use the 3-param constructor to avoid null id issues
        sampleUser = new User(1L, "Test User", "test@example.com");

        sampleEvent = new Event();
        sampleEvent.setId(2L);
        sampleEvent.setTitle("Test Event");

        sampleRegistration = new Registration();
        sampleRegistration.setId(5L);
        sampleRegistration.setRegistrationDate(LocalDateTime.now());
        sampleRegistration.setUser(sampleUser);
        sampleRegistration.setEvent(sampleEvent);
    }

    @Test
    public void testCreateRegistration() throws Exception {
        Mockito.when(registrationService.create(any(Registration.class))).thenReturn(sampleRegistration);

        Registration requestRegistration = new Registration();
        requestRegistration.setUser(sampleUser);
        requestRegistration.setEvent(sampleEvent);

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegistration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.id").value(2))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }

    @Test
    public void testGetAllRegistrations() throws Exception {
        Mockito.when(registrationService.getAll()).thenReturn(List.of(sampleRegistration));

        mockMvc.perform(get("/api/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5))
                .andExpect(jsonPath("$[0].user.name").value("Test User"))
                .andExpect(jsonPath("$[0].event.title").value("Test Event"));
    }

    @Test
    public void testGetRegistrationById_found() throws Exception {
        Mockito.when(registrationService.getById(5L)).thenReturn(sampleRegistration);

        mockMvc.perform(get("/api/registrations/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }

    @Test
    public void testGetRegistrationById_notFound() throws Exception {
        Mockito.when(registrationService.getById(999L))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                        "Registration not found with id 999"));

        mockMvc.perform(get("/api/registrations/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateRegistration() throws Exception {
        Registration updatedRegistration = new Registration();
        updatedRegistration.setId(5L);
        updatedRegistration.setRegistrationDate(LocalDateTime.now());
        updatedRegistration.setUser(sampleUser);
        updatedRegistration.setEvent(sampleEvent);

        Mockito.when(registrationService.update(eq(5L), any(Registration.class)))
                .thenReturn(updatedRegistration);

        Registration requestRegistration = new Registration();
        requestRegistration.setUser(sampleUser);
        requestRegistration.setEvent(sampleEvent);

        mockMvc.perform(put("/api/registrations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegistration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }

    @Test
    public void testUpdateRegistration_notFound() throws Exception {
        Registration requestRegistration = new Registration();
        requestRegistration.setUser(sampleUser);
        requestRegistration.setEvent(sampleEvent);

        Mockito.when(registrationService.update(eq(999L), any(Registration.class)))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                        "Registration not found with id 999"));

        mockMvc.perform(put("/api/registrations/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegistration)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteRegistrationById() throws Exception {
        Mockito.doNothing().when(registrationService).deleteById(5L);

        mockMvc.perform(delete("/api/registrations/5"))
                .andExpect(status().isOk());

        Mockito.verify(registrationService, Mockito.times(1)).deleteById(5L);
    }

    @Test
    public void testCreateRegistration_userNotFound() throws Exception {
        Registration requestRegistration = new Registration();
        requestRegistration.setUser(sampleUser);
        requestRegistration.setEvent(sampleEvent);

        Mockito.when(registrationService.create(any(Registration.class)))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                        "User not found with id 1"));

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegistration)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateRegistration_eventNotFound() throws Exception {
        Registration requestRegistration = new Registration();
        requestRegistration.setUser(sampleUser);
        requestRegistration.setEvent(sampleEvent);

        Mockito.when(registrationService.create(any(Registration.class)))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                        "Event not found with id 2"));

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegistration)))
                .andExpect(status().isNotFound());
    }
}
