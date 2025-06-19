package com.keyin.rest.registration;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<Registration> getAll() {
        return registrationRepository.findAll();
    }

    public Registration create(Registration registration) {
        // You can add validations here if needed
        return registrationRepository.save(registration);
    }

    public List<Registration> getByUser(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<Registration> getByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }
}
