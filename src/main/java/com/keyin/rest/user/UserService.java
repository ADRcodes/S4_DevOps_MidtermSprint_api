package com.keyin.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User Tag Methods //
    public List<User> getUsersByPreferredTag(String tag) {
        if (tag == null || tag.isBlank()) return List.of();
        return userRepository.findByUserTagIgnoreCase(tag.trim());
    }

    public List<String> getAllUserTags() {
        return userRepository.findAllDistinctTags();
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPreferredTags(
                    updatedUser.getPreferredTags() == null ? List.of() : updatedUser.getPreferredTags()
            );
            return userRepository.save(existingUser);
        }).orElse(null);
    }
}
