package com.keyin.rest.venue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVenues() {
        List<Venue> mockVenues = Arrays.asList(
                new Venue("Hall A", "123 Water St", 100),
                new Venue("Hall B", "456 Main St", 200)
        );

        when(venueRepository.findAll()).thenReturn(mockVenues);

        List<Venue> result = venueService.getAllVenues();

        assertEquals(2, result.size());
        verify(venueRepository, times(1)).findAll();
    }

    @Test
    void testAddVenue() {
        Venue newVenue = new Venue("New Hall", "789 Paradise Road", 150);
        when(venueRepository.save(newVenue)).thenReturn(newVenue);

        Venue result = venueService.addVenue(newVenue);

        assertEquals("New Hall", result.getName());
        verify(venueRepository, times(1)).save(newVenue);
    }

    @Test
    void testGetVenueById() {
        Venue venue = new Venue("Test Venue", "Somewhere", 80);
        venue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        Optional<Venue> result = venueService.getVenueById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Venue", result.get().getName());
    }

    @Test
    void testDeleteVenue() {
        venueService.deleteVenue(1L);
        verify(venueRepository, times(1)).deleteById(1L);
    }
}
