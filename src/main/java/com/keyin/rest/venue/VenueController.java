package com.keyin.rest.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id).orElse(null);
    }

    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.addVenue(venue);
    }

    @PutMapping("/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }
}
