package com.keyin.rest.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByVenueId(Long venueId);
    List<Event> findByOrganizerId(Long organizerId);

    @Query("select e from Event e join e.tags t where t = :tag")
    List<Event> findByTag(@Param("tag") String tag);

    @Query("select e from Event e join e.tags t where lower(t) = lower(:tag)")
    List<Event> findByTagIgnoreCase(@Param("tag") String tag);

    @Query("select distinct t from Event e join e.tags t")
    List<String> findAllDistinctTags();

}
