package com.keyin.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyin.rest.user.User;
import com.keyin.rest.user.UserRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Return minimal user info (id, name, email). Password is ignored via@JsonIgnore.
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        User existing = userRepository.findByEmail(newUser.getEmail());
        if (existing != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        // Role default "USER" is fine from your entity
        User saved = userRepository.save(newUser);
        return ResponseEntity.ok(saved);
    }
}
