package com.keyin.rest.venue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc(addFilters = false)  // Disable security filters for testing
public class VenueControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VenueService venueService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAllVenues_returnsList() throws Exception {
        Venue v = new Venue();
        v.setId(1L);
        v.setName("Test Venue");
        when(venueService.getAllVenues()).thenReturn(Arrays.asList(v));

        mvc.perform(get("/api/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Venue"));
    }

    @Test
    void getVenueById_validId_returnsVenue() throws Exception {
        Venue v = new Venue();
        v.setId(1L);
        v.setName("Main Hall");
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
        Venue v = new Venue();
        v.setId(10L);
        v.setName("New Arena");
        v.setAddress("123 Main St");
        v.setCapacity(500);

        when(venueService.addVenue(any(Venue.class))).thenReturn(v);

        Venue requestVenue = new Venue();
        requestVenue.setName("New Arena");
        requestVenue.setAddress("123 Main St");
        requestVenue.setCapacity(500);

        mvc.perform(post("/api/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestVenue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("New Arena"));
    }

    @Test
    void updateVenue_validInput_returnsUpdatedVenue() throws Exception {
        Venue v = new Venue();
        v.setId(2L);
        v.setName("Updated Venue");
        v.setAddress("456 Oak St");
        v.setCapacity(300);

        when(venueService.updateVenue(eq(2L), any(Venue.class))).thenReturn(v);

        Venue requestVenue = new Venue();
        requestVenue.setName("Updated Venue");
        requestVenue.setAddress("456 Oak St");
        requestVenue.setCapacity(300);

        mvc.perform(put("/api/venues/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestVenue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Updated Venue"));
    }

    @Test
    void updateVenue_notFound_returnsNull() throws Exception {
        when(venueService.updateVenue(eq(999L), any(Venue.class))).thenReturn(null);

        Venue requestVenue = new Venue();
        requestVenue.setName("Non-existent");
        requestVenue.setAddress("Nowhere");
        requestVenue.setCapacity(100);

        mvc.perform(put("/api/venues/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestVenue)))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // Spring returns empty body for null
    }

    @Test
    void deleteVenue_callsServiceAndReturnsOk() throws Exception {
        doNothing().when(venueService).deleteVenue(3L);

        mvc.perform(delete("/api/venues/3"))
                .andExpect(status().isOk());

        verify(venueService, times(1)).deleteVenue(3L);
    }
}
