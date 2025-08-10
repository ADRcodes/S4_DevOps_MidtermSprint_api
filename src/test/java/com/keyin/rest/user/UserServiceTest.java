// package com.keyin.rest.user;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class UserServiceTest {

//     private UserRepository userRepository;
//     private UserService userService;

//     @BeforeEach
//     public void setup() {
//         userRepository = mock(UserRepository.class);
//         userService = new UserService();
//         userService.setUserRepository(userRepository);
//     }

//     @Test
//     public void testGetAllUsers() {
//         User user = new User(1L, "Test User", "test@example.com");
//         when(userRepository.findAll()).thenReturn(Arrays.asList(user));

//         List<User> users = userService.getAllUsers();
//         assertEquals(1, users.size());
//         assertEquals("Test User", users.get(0).getName());
//     }

//     @Test
//     public void testCreateUser() {
//         User user = new User(2L, "New User", "new@example.com");
//         when(userRepository.save(user)).thenReturn(user);

//         User createdUser = userService.createUser(user);
//         assertEquals("New User", createdUser.getName());
//         verify(userRepository, times(1)).save(user);
//     }

//     @Test
//     public void testGetUserById() {
//         User user = new User(3L, "Alice", "alice@example.com");
//         when(userRepository.findById(3L)).thenReturn(Optional.of(user));

//         User result = userService.getUserById(3L);
//         assertNotNull(result);
//         assertEquals("Alice", result.getName());
//     }

//     @Test
//     public void testGetUserByEmail() {
//         User user = new User(4L, "Bob", "bob@example.com");
//         when(userRepository.findByEmail("bob@example.com")).thenReturn(user);

//         User result = userService.getUserByEmail("bob@example.com");
//         assertNotNull(result);
//         assertEquals("Bob", result.getName());
//     }

//     @Test
//     public void testDeleteUser() {
//         User user = new User(5L, "ToDelete", "delete@example.com");
//         doNothing().when(userRepository).delete(user);

//         userService.deleteUser(user);
//         verify(userRepository, times(1)).delete(user);
//     }

//     @Test
//     public void testUpdateUser() {
//         User oldUser = new User(6L, "Old Name", "old@example.com");
//         User updatedUser = new User(6L, "New Name", "new@example.com");

//         when(userRepository.findById(6L)).thenReturn(Optional.of(oldUser));
//         when(userRepository.save(any(User.class))).thenReturn(updatedUser);

//         User result = userService.updateUser(6L, updatedUser);
//         assertNotNull(result);
//         assertEquals("New Name", result.getName());
//         assertEquals("new@example.com", result.getEmail());
//     }
// }
