// package com.keyin.rest.user;

// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.when;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(UserController.class)
// @WithMockUser(username = "testuser", roles = {"USER"})  // <--- Mock user
// public class UserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private UserService userService;

//     private User user1;
//     private User user2;

//     @BeforeEach
//     void setUp() {
//         user1 = new User(1L, "Alice", "alice@example.com");
//         user2 = new User(2L, "Bob", "bob@example.com");
//     }

//     @Test
//     void testGetAllUsers() throws Exception {
//         List<User> userList = Arrays.asList(user1, user2);

//         when(userService.getAllUsers()).thenReturn(userList);

//         mockMvc.perform(get("/api/users"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.size()").value(userList.size()))
//                 .andExpect(jsonPath("$[0].name").value("Alice"))
//                 .andExpect(jsonPath("$[1].email").value("bob@example.com"));
//     }

//     @Test
//     void testCreateUser() throws Exception {
//         User newUser = new User(3L, "Charlie", "charlie@example.com");

//         when(userService.createUser(any(User.class))).thenReturn(newUser);

//         mockMvc.perform(post("/api/users")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(new ObjectMapper().writeValueAsString(newUser)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Charlie"))
//                 .andExpect(jsonPath("$.email").value("charlie@example.com"));
//     }

//     @Test
//     void testGetUserById() throws Exception {
//         when(userService.getUserById(1L)).thenReturn(user1);

//         mockMvc.perform(get("/api/users/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Alice"))
//                 .andExpect(jsonPath("$.email").value("alice@example.com"));
//     }

//     @Test
//     void testDeleteUserById() throws Exception {
//         when(userService.getUserById(1L)).thenReturn(user1);
//         doNothing().when(userService).deleteUser(user1);

//         mockMvc.perform(delete("/api/users/1"))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     void testUpdateUserById() throws Exception {
//         User updatedUser = new User(1L, "Alice Updated", "alice.updated@example.com");

//         when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

//         mockMvc.perform(put("/api/users/1")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(new ObjectMapper().writeValueAsString(updatedUser)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Alice Updated"))
//                 .andExpect(jsonPath("$.email").value("alice.updated@example.com"));
//     }
// }
