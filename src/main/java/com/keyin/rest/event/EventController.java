package com.keyin.rest.event;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Validated
@CrossOrigin(origins = "*") // Allow all origins, adjust as needed
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> list(
            @RequestParam(required = false) Long venueId,
            @RequestParam(required = false) Long organizerId
    ) {
        if (venueId != null) {
            return service.getEventsByVenue(venueId);
        }
        if (organizerId != null) {
            return service.getEventsByOrganizer(organizerId);
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event created = service.create(event);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        return service.update(id, event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-tag/{tag}")
    public List<Event> byTag(@PathVariable String tag) {
        return service.getByTag(tag);
    }

    @GetMapping("/tags")
    public List<String> allTags() {
        return service.getAllEventTags();
    }

    // Optional conveniences to mutate just the tags
    @PostMapping("/{id}/tags")
    public Event addTag(@PathVariable Long id, @RequestParam String tag) {
        return service.addTag(id, tag);
    }

    @DeleteMapping("/{id}/tags")
    public Event removeTag(@PathVariable Long id, @RequestParam String tag) {
        return service.removeTag(id, tag);
    }

}
