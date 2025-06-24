package com.keyin.rest.venue;

import jakarta.persistence.*;
import java.util.List;
import com.keyin.rest.event.Event;


@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private int capacity;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events;

    public Venue() {}

    public Venue(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}
