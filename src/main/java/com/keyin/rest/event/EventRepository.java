package com.keyin.rest.event;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {
    // No additional methods needed for basic CRUD operations
    // JpaRepository provides methods like findAll, findById, save, deleteById, etc.
}
