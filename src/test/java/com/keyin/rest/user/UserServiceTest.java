package com.keyin.rest.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_savesAndReturns() {
        User toSave = new User(null, "Alice Smith", "alice@example.com");
        User saved = new User(1L, "Alice Smith", "alice@example.com");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.createUser(toSave);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Alice Smith");
        assertThat(result.getEmail()).isEqualTo("alice@example.com");

        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                new User(1L, "Bob Johnson", "bob@example.com"),
                new User(2L, "Charlie Brown", "charlie@example.com")
        ));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2)
                .extracting(User::getName)
                .containsExactly("Bob Johnson", "Charlie Brown");

        verify(userRepository).findAll();
    }

    @Test
    void getUserById_found_returnsUser() {
        User u = new User(3L, "David Miller", "david@example.com");
        when(userRepository.findById(3L)).thenReturn(Optional.of(u));

        User result = userService.getUserById(3L);

        assertThat(result).isEqualTo(u);
        verify(userRepository).findById(3L);
    }


    @Test
    void getUserById_missing_returnsNull() {
        // The service returns null when user is not found (via orElse(null))
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User result = userService.getUserById(99L);

        assertThat(result).isNull();
        verify(userRepository).findById(99L);
    }

    @Test
    void getUserByEmail_found_returnsUser() {
        User u = new User(4L, "Eve Adams", "eve@example.com");
        when(userRepository.findByEmail("eve@example.com")).thenReturn(u);

        User result = userService.getUserByEmail("eve@example.com");

        assertThat(result).isEqualTo(u);
        verify(userRepository).findByEmail("eve@example.com");
    }

    @Test
    void updateUser_found_mergesAndSaves() {
        User existing = new User(5L, "Frank Green", "frank@example.com");
        User patch = new User(5L, "Frank Updated", "frank.updated@example.com");

        when(userRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.updateUser(5L, patch);

        assertThat(result.getName()).isEqualTo("Frank Updated");
        assertThat(result.getEmail()).isEqualTo("frank.updated@example.com");

        verify(userRepository).findById(5L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_missing_returnsNull() {
        // The service returns null when user is not found (via orElse(null))
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        User result = userService.updateUser(42L, new User());

        assertThat(result).isNull();
        verify(userRepository).findById(42L);
        verify(userRepository, never()).save(any());
    }


    @Test
    void deleteUser_deletesEntity() {
        User existing = new User(6L, "Grace Hill", "grace@example.com");

        doNothing().when(userRepository).delete(existing);

        userService.deleteUser(existing);

        verify(userRepository).delete(existing);
    }
}

