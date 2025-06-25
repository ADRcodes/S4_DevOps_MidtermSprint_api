package com.keyin.rest.event;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventService {
    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public List<Event> getAll() {
        return repo.findAll();
    }

    public Event getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event not found with id " + id));
    }

    public List<Event> getEventsByVenue(Long venueId) {
        return repo.findByVenueId(venueId);
    }

    public List<Event> getEventsByOrganizer(Long organizerId) {
        return repo.findByOrganizerId(organizerId);
    }

    public Event create(Event event) {
        return repo.save(event);
    }

    public Event update(Long id, Event updated) {
        Event existing = getById(id);
        existing.setCompany(updated.getCompany());
        existing.setTitle(updated.getTitle());
        existing.setDate(updated.getDate());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCapacity(updated.getCapacity());
        existing.setVenue(updated.getVenue());
        existing.setOrganizer(updated.getOrganizer());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Event existing = getById(id);
        repo.delete(existing);
    }



}
