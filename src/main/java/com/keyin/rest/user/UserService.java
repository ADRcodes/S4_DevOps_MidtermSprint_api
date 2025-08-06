package com.keyin.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<User> getUsersByUserTag(String userTag) {
        return userRepository.findByUserTagIgnoreCase(userTag);
    }

    public List<User> getUsersByUserTagCaseSensitive(String userTag) {
        return userRepository.findByUserTag(userTag);
    }

    public List<String> getAllUserTags() {
        return userRepository.findAll().stream()
                .map(User::getUserTag)
                .distinct()
                .toList();
    }

    public User updateUserTag(Long id, String userTag) {
        Optional<User> userToUpdateOptional = userRepository.findById(id);

        if (userToUpdateOptional.isPresent()) {
            User userToUpdate = userToUpdateOptional.get();
            userToUpdate.setUserTag(userTag);
            return userRepository.save(userToUpdate);
        }

        return null;
    }


    // Updates User details including User Tag //
    public User updateUser(Long id, User updatedUser) {
        Optional<User> userToUpdateOptional = userRepository.findById(id);

        if (userToUpdateOptional.isPresent()) {
            User userToUpdate = userToUpdateOptional.get();
            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setUserTag(updatedUser.getUserTag());
            return userRepository.save(userToUpdate);
        }

        return null;
    }

}
