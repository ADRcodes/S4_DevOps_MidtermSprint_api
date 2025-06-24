package com.keyin.rest.registration;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventRepository;
import com.keyin.rest.user.User;
import com.keyin.rest.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Registration create(Registration registration) {
        Long userId = registration.getUser().getId();
        Long eventId = registration.getEvent().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found with id " + eventId));

        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());

        return registrationRepository.save(registration);
    }

    public List<Registration> getAll() {
        return registrationRepository.findAll();
    }

    public Registration getById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Registration not found with id " + id));
    }

    public void deleteById(Long id) {
        registrationRepository.deleteById(id);
    }
}
