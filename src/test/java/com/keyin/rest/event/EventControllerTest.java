package com.keyin.rest.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void list_returnsJsonArray() throws Exception {
        Event e = new Event(); e.setId(1L);
        when(service.getAll()).thenReturn(Arrays.asList(e));

        mvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void create_validInput_returnsCreated() throws Exception {
        Event e = new Event(); e.setId(5L);
        when(service.create(any())).thenReturn(e);

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(service.getById(99L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Event not found with id 99"));

        mvc.perform(get("/api/events/99"))
                .andExpect(status().isNotFound());
    }
}
