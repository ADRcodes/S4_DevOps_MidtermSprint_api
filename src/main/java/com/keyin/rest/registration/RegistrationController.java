package com.keyin.rest.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
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
}
