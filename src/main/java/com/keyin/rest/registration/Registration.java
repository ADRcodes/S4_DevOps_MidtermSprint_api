package com.keyin.rest.registration;

import com.keyin.rest.event.Event;
import com.keyin.rest.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private LocalDateTime registrationDate;

    // Constructors
    public Registration() {}

    public Registration(User user, Event event, LocalDateTime registrationDate) {
        this.user = user;
        this.event = event;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}
