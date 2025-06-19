package com.keyin.rest.registration;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<Registration> getAll() {
        return registrationService.getAll();
    }

    @PostMapping
    public Registration create(@RequestBody Registration registration) {
        return registrationService.create(registration);
    }

    @GetMapping("/user/{userId}")
    public List<Registration> getByUser(@PathVariable Long userId) {
        return registrationService.getByUser(userId);
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getByEvent(@PathVariable Long eventId) {
        return registrationService.getByEvent(eventId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        registrationService.delete(id);
    }
}
