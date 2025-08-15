package com.keyin.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean UserService userService;

    @Test
    void listUsers_returnsJsonArray() throws Exception {
        var u = new User(1L, "Alice Smith", "alice@example.com");
        when(userService.getAllUsers()).thenReturn(List.of(u));

        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice Smith"))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"));
    }

    @Test
    void getUserById_found_returnsUser() throws Exception {
        var u = new User(6L, "Noah Lin", "noah.lin@stackzone.dev");
        when(userService.getUserById(6L)).thenReturn(u);

        mvc.perform(get("/api/users/6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Noah Lin"))
                .andExpect(jsonPath("$.email").value("noah.lin@stackzone.dev"));
    }

    @Test
    void getUserById_missing_returns404() throws Exception {
        when(userService.getUserById(999L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User 999"));

        mvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserById_valid_returnsUpdatedUser() throws Exception {
        var updated = new User(1L, "Alice Updated", "alice.updated@example.com");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        mvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Alice Updated"))
                .andExpect(jsonPath("$.email").value("alice.updated@example.com"));
    }

    @Test
    void updateUserById_missing_returnsNull() throws Exception {
        // The service returns null when user is not found
        when(userService.updateUser(eq(42L), any(User.class))).thenReturn(null);

        mvc.perform(put("/api/users/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new User(0L, "X", "x@x.com"))))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // Spring returns empty body for null
    }

    @Test
    void deleteUserById_fetchesThenDeletes_returnsOk() throws Exception {
        var existing = new User(11L, "Jake Sanders", "jsanders@devmail.io");
        when(userService.getUserById(11L)).thenReturn(existing);
        doNothing().when(userService).deleteUser(existing);

        mvc.perform(delete("/api/users/11"))
                .andExpect(status().isOk());

        verify(userService).getUserById(11L);
        verify(userService).deleteUser(existing);
        verifyNoMoreInteractions(userService);
    }
}
