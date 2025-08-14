package com.keyin.rest.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.rest.user.User;
import com.keyin.rest.venue.Venue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(EventController.class)
@WithMockUser // This bypasses Spring Security for tests
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean // Using the standard @MockBean since Spring Boot 3.3.5 supports it
    private EventService service;

    @Autowired
    private ObjectMapper mapper;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = createValidEvent();
    }

    @Test
    void list_returnsJsonArray() throws Exception {
        testEvent.setId(1L);
        when(service.getAll()).thenReturn(Arrays.asList(testEvent));

        mvc.perform(get("/api/events")
                        .with(csrf())) // Add CSRF token for security
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Event"));
    }

    @Test
    void get_existingEvent_returnsEvent() throws Exception {
        testEvent.setId(1L);
        when(service.getById(1L)).thenReturn(testEvent);

        mvc.perform(get("/api/events/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Event"));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(service.getById(99L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Event not found with id 99"));

        mvc.perform(get("/api/events/99")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void create_validInput_returnsCreated() throws Exception {
        testEvent.setId(5L);
        when(service.create(any(Event.class))).thenReturn(testEvent);

        mvc.perform(post("/api/events")
                        .with(csrf()) // Required for POST requests with Spring Security
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testEvent)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Test Event"));

        verify(service, times(1)).create(any(Event.class));
    }

    @Test
    void listByVenue_returnsFilteredEvents() throws Exception {
        testEvent.setId(1L);
        when(service.getEventsByVenue(1L)).thenReturn(Arrays.asList(testEvent));

        mvc.perform(get("/api/events")
                        .param("venueId", "1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service, never()).getAll();
        verify(service, times(1)).getEventsByVenue(1L);
    }

    @Test
    void listByOrganizer_returnsFilteredEvents() throws Exception {
        testEvent.setId(1L);
        when(service.getEventsByOrganizer(1L)).thenReturn(Arrays.asList(testEvent));

        mvc.perform(get("/api/events")
                        .param("organizerId", "1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service, never()).getAll();
        verify(service, times(1)).getEventsByOrganizer(1L);
    }

    @Test
    void byTag_returnsEventsWithTag() throws Exception {
        testEvent.setId(1L);
        when(service.getByTag("Java")).thenReturn(Arrays.asList(testEvent));

        mvc.perform(get("/api/events/by-tag/Java")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service, times(1)).getByTag("Java");
    }

    @Test
    void update_validInput_returnsUpdated() throws Exception {
        testEvent.setId(1L);
        testEvent.setTitle("Updated Event");
        when(service.update(eq(1L), any(Event.class))).thenReturn(testEvent);

        mvc.perform(put("/api/events/1")
                        .with(csrf()) // Required for PUT requests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testEvent)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Event"));

        verify(service, times(1)).update(eq(1L), any(Event.class));
    }

    @Test
    void delete_existingEvent_returnsNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/events/1")
                        .with(csrf())) // Required for DELETE requests
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void create_withInvalidData_returnsBadRequest() throws Exception {
        when(service.create(any(Event.class))).thenThrow(
                new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST,
                        "Event must have at least one non-blank tag"));

        Event invalidEvent = createValidEvent();
        invalidEvent.setTags(List.of()); // This should trigger validation error

        mvc.perform(post("/api/events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidEvent)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTag_existingEvent_returnsUpdatedEvent() throws Exception {
        testEvent.setId(1L);
        when(service.addTag(1L, "NewTag")).thenReturn(testEvent);

        mvc.perform(post("/api/events/1/tags")
                        .param("tag", "NewTag")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).addTag(1L, "NewTag");
    }

    @Test
    void removeTag_existingEvent_returnsUpdatedEvent() throws Exception {
        testEvent.setId(1L);
        when(service.removeTag(1L, "Java")).thenReturn(testEvent);

        mvc.perform(delete("/api/events/1/tags")
                        .param("tag", "Java")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).removeTag(1L, "Java");
    }

    @Test
    void getAllTags_returnsTagList() throws Exception {
        List<String> tags = Arrays.asList("Java", "Spring", "React");
        when(service.getAllEventTags()).thenReturn(tags);

        mvc.perform(get("/api/events/tags")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("Java"));

        verify(service, times(1)).getAllEventTags();
    }

    // Helper method to create a valid event for testing
    private Event createValidEvent() {
        Event event = new Event();
        event.setCompany("Test Company");
        event.setTitle("Test Event");
        event.setDate(LocalDateTime.of(2025, 12, 25, 10, 0)); // Fixed future date
        event.setDescription("Test Description for event");
        event.setPrice(new BigDecimal("99.99"));
        event.setCapacity(100);

        // Create organizer
        User organizer = new User();
        organizer.setId(1L);
        organizer.setName("Test Organizer");
        organizer.setEmail("organizer@test.com");
        event.setOrganizer(organizer);

        // Create venue
        Venue venue = new Venue();
        venue.setId(1L);
        venue.setName("Test Venue");
        venue.setAddress("123 Test St");
        venue.setCapacity(200);
        event.setVenue(venue);

        // Add required tags
        event.setTags(List.of("Java", "Spring"));

        return event;
    }
}