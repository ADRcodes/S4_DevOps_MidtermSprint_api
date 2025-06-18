//package com.keyin.rest.event;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class EventServiceTest {
//    @Mock
//    private EventRepository repo;
//
//    @InjectMocks
//    private EventService service;
//
//    @Test
//    void getAll_returnsList(){
//        when(repo.findAll()).thenReturn(Collections.emptyList());
//        assertThat(service.getAll()).isEmpty();
//    }
//
//    @Test
//    void getById_whenFound_returnsEvent() {
//        Event e = new Event(); e.setId(1L);
//        when(repo.findById(1L)).thenReturn(Optional.of(e));
//        assertThat(service.getById(1L).getId()).isEqualTo(1L);
//    }
//
//    @Test
//    void getById_whenNotFound_throws404() {
//        when(repo.findById(2L)).thenReturn(Optional.empty());
//        assertThatThrownBy(() -> service.getById(2L))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("Event not found with id 2");
//    }
//
//    @Test
//    void create_savesAndReturns() {
//        Event e = new Event();
//        when(repo.save(e)).thenReturn(e);
//        assertThat(service.create(e)).isSameAs(e);
//    }
//
//    @Test
//    void update_modifiesAndSaves() {
//        Event existing = new Event(); existing.setId(3L);
//        Event updated = new Event(); updated.setCompany("Co");
//        when(repo.findById(3L)).thenReturn(Optional.of(existing));
//        when(repo.save(existing)).thenReturn(existing);
//
//        Event result = service.update(3L, updated);
//        assertThat(result.getCompany()).isEqualTo("Co");
//    }
//
//    @Test
//    void delete_removesEntity() {
//        Event e = new Event(); e.setId(4L);
//        when(repo.findById(4L)).thenReturn(Optional.of(e));
//        service.delete(4L);
//        verify(repo).delete(e);
//    }
//}
