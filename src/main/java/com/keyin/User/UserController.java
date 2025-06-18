package com.keyin.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

}
