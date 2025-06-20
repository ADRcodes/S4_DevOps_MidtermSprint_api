package com.keyin.rest.registration;

import com.keyin.rest.event.Event;
import com.keyin.rest.event.EventRepository;
import com.keyin.rest.user.User;
import com.keyin.rest.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Registration create(Registration registration) {
        User user = userRepository.findById(registration.getUser().getId()).orElse(null);
        Event event = eventRepository.findById(registration.getEvent().getId()).orElse(null);

        if (user == null || event == null) {
            throw new RuntimeException("User or Event not found");
        }

        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());

        return registrationRepository.save(registration);
    }

    public List<Registration> getAll() {
        return registrationRepository.findAll();
    }
}
