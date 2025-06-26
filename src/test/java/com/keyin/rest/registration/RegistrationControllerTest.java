package com.keyin.rest.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.rest.event.Event;
import com.keyin.rest.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistrationService registrationService;

    private Registration sampleRegistration;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");

        Event event = new Event();
        event.setId(2L);
        event.setTitle("Test Event");

        sampleRegistration = new Registration();
        sampleRegistration.setId(5L);
        sampleRegistration.setRegistrationDate(LocalDateTime.now());
        sampleRegistration.setUser(user);
        sampleRegistration.setEvent(event);
    }

    @Test
    public void testCreateRegistration() throws Exception {
        Mockito.when(registrationService.create(any(Registration.class))).thenReturn(sampleRegistration);

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRegistration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }

    @Test
    public void testGetAllRegistrations() throws Exception {
        Mockito.when(registrationService.getAll()).thenReturn(List.of(sampleRegistration));

        mockMvc.perform(get("/api/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.name").value("Test User"))
                .andExpect(jsonPath("$[0].event.title").value("Test Event"));
    }

    @Test
    public void testGetRegistrationById() throws Exception {
        Mockito.when(registrationService.getById(5L)).thenReturn(sampleRegistration);

        mockMvc.perform(get("/api/registrations/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }

    @Test
    public void testDeleteRegistrationById() throws Exception {
        mockMvc.perform(delete("/api/registrations/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRegistration() throws Exception {
        Registration updatedRegistration = new Registration();
        updatedRegistration.setId(5L);
        updatedRegistration.setRegistrationDate(LocalDateTime.now());
        updatedRegistration.setUser(sampleRegistration.getUser());
        updatedRegistration.setEvent(sampleRegistration.getEvent());

        Mockito.when(registrationService.update(Mockito.eq(5L), any(Registration.class)))
                .thenReturn(updatedRegistration);

        mockMvc.perform(put("/api/registrations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRegistration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("Test User"))
                .andExpect(jsonPath("$.event.title").value("Test Event"));
    }
}
