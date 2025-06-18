package com.keyin.rest.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public Venue addVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue updatedVenue) {
        return venueRepository.findById(id)
                .map(v -> {
                    v.setName(updatedVenue.getName());
                    v.setAddress(updatedVenue.getAddress());
                    v.setCapacity(updatedVenue.getCapacity());
                    return venueRepository.save(v);
                }).orElse(null);
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
