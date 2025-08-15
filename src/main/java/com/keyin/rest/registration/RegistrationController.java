package com.keyin.rest.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public Registration create(@RequestBody Registration registration) {
        return registrationService.create(registration);
    }

    @GetMapping
    public List<Registration> getAll() {
        return registrationService.getAll();
    }

    @GetMapping("/{id}")
    public Registration getById(@PathVariable Long id) {
        return registrationService.getById(id);
    }

    @PutMapping("/{id}")
    public Registration update(@PathVariable Long id, @RequestBody Registration updatedRegistration) {
        return registrationService.update(id, updatedRegistration);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        registrationService.deleteById(id);
    }
}
