package com.keyin.rest.event;

import com.keyin.rest.user.User;
import com.keyin.rest.venue.Venue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id", nullable = false)
    @NotNull(message = "Venue is required")
    @JsonIgnoreProperties("events")  // prevents recursion
    private Venue venue;

    @ElementCollection
    @CollectionTable(name = "event_tags", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "tag")
    @NotNull(message = "Tags are required")
    @Size(min = 1, message = "Event must have at least one tag")
    private List<@NotBlank(message = "Tags cannot be blank") String> tags = new ArrayList<>();

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

    // Getters and Setters for tags //
    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = (tags == null) ? new ArrayList<>() : tags; }

    // Methods for tags //

    public void addTag(String tag) {
        if (tags == null) tags = new ArrayList<>();
        if (tag != null && !tag.isBlank() && !tags.contains(tag)) tags.add(tag.trim());
    }
    public void removeTag(String tag) {
        if (tags != null) tags.removeIf(t -> t.equalsIgnoreCase(tag));
    }
}
