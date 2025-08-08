package com.keyin.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keyin.rest.user.User;
import com.keyin.rest.user.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String loginUser(User loginData) {
        User user = userRepository.findByEmail(loginData.getEmail());

        if (user == null || !passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
            return "Invalid email or password";
        }

        return "Login successful!";
    }
}
