package com.keyin.rest.event;

import com.keyin.rest.user.User;
import com.keyin.rest.venue.Venue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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

    @NotBlank (message = "Company is required")
    private String company;

    @NotBlank(message = "Event title is required")
    private String title;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDateTime date;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description can be at most 2000 characters")
    @Column(length = 2000)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private BigDecimal price;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @NotNull(message = "Venue is required")
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    @NotNull(message = "Venue is required")
    private Venue venue;

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

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

}
