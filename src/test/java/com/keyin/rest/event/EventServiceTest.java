package com.keyin.rest.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository repo;

    @InjectMocks
    private EventService service;

    @Test
    void getAll_returnsList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        assertThat(service.getAll()).isEmpty();
    }

    @Test
    void getById_whenFound_returnsEvent() {
        Event e = new Event();
        e.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(e));
        assertThat(service.getById(1L).getId()).isEqualTo(1L);
    }

    @Test
    void getById_whenNotFound_throws404() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(2L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Event not found with id 2");
    }

    @Test
    void create_withValidTags_savesAndReturns() {
        Event e = new Event();
        e.setTags(List.of("Java", "Spring"));
        when(repo.save(e)).thenReturn(e);

        Event result = service.create(e);
        assertThat(result).isSameAs(e);
        assertThat(e.getTags()).containsExactly("Java", "Spring");
    }

    @Test
    void create_withEmptyTags_throwsBadRequest() {
        Event e = new Event();
        e.setTags(new ArrayList<>());

        assertThatThrownBy(() -> service.create(e))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Event must have at least one non-blank tag");
    }


    @Test
    void update_withValidTags_modifiesAndSaves() {
        Event existing = new Event();
        existing.setId(3L);
        existing.setTags(new ArrayList<>(List.of("OldTag")));

        Event updated = new Event();
        updated.setCompany("Co");
        updated.setTags(List.of("NewTag"));

        when(repo.findById(3L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        Event result = service.update(3L, updated);
        assertThat(result.getCompany()).isEqualTo("Co");
        assertThat(result.getTags()).containsExactly("NewTag");
        verify(repo).save(existing);
    }


    @Test
    void delete_removesEntity() {
        Event e = new Event();
        e.setId(4L);
        when(repo.findById(4L)).thenReturn(Optional.of(e));

        service.delete(4L);

        verify(repo).delete(e);
    }

    @Test
    void getEventsByVenue_returnsCorrectEvents() {
        List<Event> events = List.of(new Event());
        when(repo.findByVenueId(1L)).thenReturn(events);
        assertThat(service.getEventsByVenue(1L)).isEqualTo(events);
    }

    @Test
    void getEventsByOrganizer_returnsCorrectEvents() {
        List<Event> events = List.of(new Event());
        when(repo.findByOrganizerId(1L)).thenReturn(events);
        assertThat(service.getEventsByOrganizer(1L)).isEqualTo(events);
    }

    @Test
    void addTag_addsTagAndSaves() {
        Event e = new Event();
        e.setId(5L);
        e.setTags(new ArrayList<>(List.of("ExistingTag")));
        when(repo.findById(5L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.addTag(5L, "NewTag");
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // The addTag method in Event class adds the tag to the list
        assertThat(e.getTags()).contains("NewTag");
    }

    @Test
    void addTag_withBlankTag_doesNotAddTag() {
        Event e = new Event();
        e.setId(5L);
        e.setTags(new ArrayList<>(List.of("ExistingTag")));
        when(repo.findById(5L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.addTag(5L, "   ");
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // Blank tags are not added per Event.addTag() logic
        assertThat(e.getTags()).containsExactly("ExistingTag");
    }

    @Test
    void addTag_withDuplicateTag_doesNotAddDuplicate() {
        Event e = new Event();
        e.setId(5L);
        e.setTags(new ArrayList<>(List.of("ExistingTag")));
        when(repo.findById(5L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.addTag(5L, "ExistingTag");
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // Duplicate tags are not added per Event.addTag() logic
        assertThat(e.getTags()).containsExactly("ExistingTag");
    }

    @Test
    void removeTag_removesTagAndSaves() {
        Event e = new Event();
        e.setId(6L);
        e.setTags(new ArrayList<>(List.of("Tag1", "Tag2")));
        when(repo.findById(6L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.removeTag(6L, "Tag1");
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // The removeTag method in Event class removes the tag
        assertThat(e.getTags()).containsExactly("Tag2");
    }

    @Test
    void removeTag_withCaseInsensitiveMatch_removesTag() {
        Event e = new Event();
        e.setId(6L);
        e.setTags(new ArrayList<>(List.of("Tag1", "Tag2")));
        when(repo.findById(6L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.removeTag(6L, "TAG1"); // Different case
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // Event.removeTag() uses equalsIgnoreCase
        assertThat(e.getTags()).containsExactly("Tag2");
    }

    @Test
    void removeTag_nonExistentTag_doesNothing() {
        Event e = new Event();
        e.setId(6L);
        e.setTags(new ArrayList<>(List.of("Tag1", "Tag2")));
        when(repo.findById(6L)).thenReturn(Optional.of(e));
        when(repo.save(e)).thenReturn(e);

        Event result = service.removeTag(6L, "NonExistentTag");
        assertThat(result).isSameAs(e);
        verify(repo).save(e);
        // Tags remain unchanged
        assertThat(e.getTags()).containsExactly("Tag1", "Tag2");
    }

    @Test
    void getByTag_withValidTag_returnsEvents() {
        List<Event> events = List.of(new Event());
        when(repo.findByTagIgnoreCase("java")).thenReturn(events);
        assertThat(service.getByTag("java")).isEqualTo(events);
    }

    @Test
    void getByTag_trimsInputTag() {
        List<Event> events = List.of(new Event());
        when(repo.findByTagIgnoreCase("java")).thenReturn(events);
        assertThat(service.getByTag("  java  ")).isEqualTo(events);
        verify(repo).findByTagIgnoreCase("java"); // Verify it was trimmed
    }

    @Test
    void getByTag_withBlankTag_throwsBadRequest() {
        assertThatThrownBy(() -> service.getByTag(""))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tag is required");
    }

    @Test
    void getByTag_withNullTag_throwsBadRequest() {
        assertThatThrownBy(() -> service.getByTag(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tag is required");
    }

    @Test
    void getByTag_withWhitespaceOnlyTag_throwsBadRequest() {
        assertThatThrownBy(() -> service.getByTag("   "))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tag is required");
    }

    @Test
    void getAllEventTags_returnsAllTags() {
        List<String> tags = List.of("Java", "Spring", "Testing");
        when(repo.findAllDistinctTags()).thenReturn(tags);
        assertThat(service.getAllEventTags()).isEqualTo(tags);
    }
}
