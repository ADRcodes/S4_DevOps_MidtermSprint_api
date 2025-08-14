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
        Venue v1 = new Venue("Hall A", "123 Water St", 100);
        v1.setId(1L);
        Venue v2 = new Venue("Hall B", "456 Main St", 200);
        v2.setId(2L);

        List<Venue> mockVenues = Arrays.asList(v1, v2);

        when(venueRepository.findAll()).thenReturn(mockVenues);

        List<Venue> result = venueService.getAllVenues();

        assertEquals(2, result.size());
        assertEquals("Hall A", result.get(0).getName());
        assertEquals("Hall B", result.get(1).getName());
        verify(venueRepository, times(1)).findAll();
    }

    @Test
    void testAddVenue() {
        Venue newVenue = new Venue("New Hall", "789 Paradise Road", 150);

        Venue savedVenue = new Venue("New Hall", "789 Paradise Road", 150);
        savedVenue.setId(1L);

        when(venueRepository.save(any(Venue.class))).thenReturn(savedVenue);

        Venue result = venueService.addVenue(newVenue);

        assertNotNull(result);
        assertEquals("New Hall", result.getName());
        assertEquals(1L, result.getId());
        verify(venueRepository, times(1)).save(newVenue);
    }

    @Test
    void testGetVenueById_found() {
        Venue venue = new Venue("Test Venue", "Somewhere", 80);
        venue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        Optional<Venue> result = venueService.getVenueById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Venue", result.get().getName());
        assertEquals(80, result.get().getCapacity());
        verify(venueRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVenueById_notFound() {
        when(venueRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Venue> result = venueService.getVenueById(999L);

        assertFalse(result.isPresent());
        verify(venueRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateVenue_found() {
        Venue existingVenue = new Venue("Old Name", "Old Address", 100);
        existingVenue.setId(1L);

        Venue updatedVenue = new Venue("New Name", "New Address", 200);

        Venue savedVenue = new Venue("New Name", "New Address", 200);
        savedVenue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(existingVenue));
        when(venueRepository.save(any(Venue.class))).thenReturn(savedVenue);

        Venue result = venueService.updateVenue(1L, updatedVenue);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("New Address", result.getAddress());
        assertEquals(200, result.getCapacity());
        assertEquals(1L, result.getId());

        verify(venueRepository, times(1)).findById(1L);
        verify(venueRepository, times(1)).save(any(Venue.class));
    }

    @Test
    void testUpdateVenue_notFound() {
        Venue updatedVenue = new Venue("New Name", "New Address", 200);

        when(venueRepository.findById(999L)).thenReturn(Optional.empty());

        Venue result = venueService.updateVenue(999L, updatedVenue);

        assertNull(result);
        verify(venueRepository, times(1)).findById(999L);
        verify(venueRepository, never()).save(any(Venue.class));
    }

    @Test
    void testDeleteVenue() {
        doNothing().when(venueRepository).deleteById(1L);

        venueService.deleteVenue(1L);

        verify(venueRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVenue_nonExistent() {
        // deleteById doesn't throw exception by default even if ID doesn't exist
        doNothing().when(venueRepository).deleteById(999L);

        venueService.deleteVenue(999L);

        verify(venueRepository, times(1)).deleteById(999L);
    }

}