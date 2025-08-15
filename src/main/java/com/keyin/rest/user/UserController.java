package com.keyin.rest.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
   public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/search")
    public User getUserByNameOrEmail(@RequestParam (required =false)String name, @RequestParam (required =false)String email) {
        if (name != null && !name.isEmpty()) {
            return userService.getAllUsers().stream()
                    .filter(user -> user.getName().equalsIgnoreCase(name)) //Ignores casing
                    .findFirst()
                    .orElse(null);
        } else if (email != null && !email.isEmpty()) {
            return userService.getUserByEmail(email);
        }
        return null; // Return null if neither name nor email is provided
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/by-name/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getAllUsers().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/users/by-email")
    public User getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        User userToDelete = userService.getUserById(id);
        if (userToDelete != null) {
            userService.deleteUser(userToDelete);
        }
    }

    // User tag endpoints //
    @GetMapping("/users/by-category/{categoryTag}")
    public List<User> getUsersByUserTag(@PathVariable("categoryTag") String preferredTag) {
        return userService.getUsersByPreferredTag(preferredTag);
    }

    @GetMapping("/users/categories")
    public List<String> getAllUserTags() {
        return userService.getAllUserTags();
    }

    @GetMapping("/organizers")
    public List<UserSummary> getOrganizers() {

    return userService.getAllUsers().stream()
            .map(user -> new UserSummary(user.getId(), user.getName()))
            .toList();
}

public static class UserSummary {
    private Long id;
    private String name;

    public UserSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
}
