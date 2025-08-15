package com.keyin.rest.event;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventRepository;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    // Validate and normalize tags for an event //
    private List<String> normalizeAndValidateTags(List<String> tags) {
        if (tags == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event must have at least one tag");
        }

        // Trim, drop blanks, deduplicate //
        List<String> cleaned = tags.stream()
                .map(t -> t == null ? "" : t.trim())
                .filter(t -> !t.isEmpty())
                .distinct()
                .toList();

        if (cleaned.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event must have at least one non-blank tag");
        }
        return cleaned;
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
        event.setTags(normalizeAndValidateTags(event.getTags()));
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
        existing.setTags(updated.getTags() == null ? List.of() : updated.getTags());
        existing.setTags(normalizeAndValidateTags(
                updated.getTags() // Reject null or empty on purpose //
        ));
        return repo.save(existing);
    }

    public void delete(Long id) {
        Event existing = getById(id);
        repo.delete(existing);
    }

    public Event addTag(Long id, String tag) {
        Event e = getById(id);
        e.addTag(tag);
        return repo.save(e);
    }
    public Event removeTag(Long id, String tag) {
        Event e = getById(id);
        e.removeTag(tag);
        return repo.save(e);
    }

    public List<Event> getByTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag is required");
        }
        return repo.findByTagIgnoreCase(tag.trim());
    }

    public List<String> getAllEventTags() {
        return repo.findAllDistinctTags();
    }

}
