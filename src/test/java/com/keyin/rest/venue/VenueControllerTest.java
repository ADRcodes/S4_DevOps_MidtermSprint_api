package com.keyin.rest.venue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VenueController.class)
public class VenueControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VenueService venueService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAllVenues_returnsList() throws Exception {
        Venue v = new Venue(); v.setId(1L); v.setName("Test Venue");
        when(venueService.getAllVenues()).thenReturn(Arrays.asList(v));

        mvc.perform(get("/api/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Venue"));
    }

    @Test
    void getVenueById_validId_returnsVenue() throws Exception {
        Venue v = new Venue(); v.setId(1L); v.setName("Main Hall");
        when(venueService.getVenueById(1L)).thenReturn(Optional.of(v));

        mvc.perform(get("/api/venues/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main Hall"));
    }

    @Test
    void getVenueById_notFound_returnsNull() throws Exception {
        when(venueService.getVenueById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/venues/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // returns null in body
    }

    @Test
    void createVenue_validInput_returnsCreatedVenue() throws Exception {
        Venue v = new Venue(); v.setId(10L); v.setName("New Arena");
        when(venueService.addVenue(any())).thenReturn(v);

        mvc.perform(post("/api/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(v)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("New Arena"));
    }

    @Test
    void updateVenue_validInput_returnsUpdatedVenue() throws Exception {
        Venue v = new Venue(); v.setId(2L); v.setName("Updated Venue");
        when(venueService.updateVenue(eq(2L), any())).thenReturn(v);

        mvc.perform(put("/api/venues/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(v)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Updated Venue"));
    }

    @Test
    void deleteVenue_callsServiceAndReturnsOk() throws Exception {
        doNothing().when(venueService).deleteVenue(3L);

        mvc.perform(delete("/api/venues/3"))
                .andExpect(status().isOk());
    }
}
