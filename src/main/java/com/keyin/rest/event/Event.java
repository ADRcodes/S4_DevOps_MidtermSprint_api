package com.keyin.rest.event;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "event_sequence")
    private Long id;

    private String company;

    private String title;

    private LocalDateTime date;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    private int capacity;

//    @ManyToOne
//    @JoinColumn(name = "organizer_id", nullable = false)
//    private User organizer;

//    @ManyToOne
//    @joinColumn(name = "venue_id", nullable = false)
//    private Venue venue;

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

//    public User getOrganizer() {
//        return organizer;
//    }
//
//    public void setOrganizer(User organizer) {
//        this.organizer = organizer;
//    }

//    public Venue getVenue() {
//        return venue;
//    }

//    public void setVenue(Venue venue) {
//        this.venue = venue;
//    }

}
