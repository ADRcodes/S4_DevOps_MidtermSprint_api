package com.keyin.rest.user;

import com.keyin.rest.user.userexception.DuplicateEmailException;
import com.keyin.rest.user.userexception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    }

    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User createUser(User newUser) {

        User existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser != null) {
            throw new DuplicateEmailException(newUser.getEmail());
        }

        return userRepository.save(newUser);
    }

    public User updateUser(Long id, User updatedUser) {
        User userToUpdate = getUserById(id); // This will throw UserNotFoundException if not found

        // Check if the email is being changed to an existing email //
        if (!userToUpdate.getEmail().equals(updatedUser.getEmail())) {
            User existingUser = userRepository.findByEmail(updatedUser.getEmail());
            if (existingUser != null && existingUser.getId() != id) {
                throw new DuplicateEmailException(updatedUser.getEmail());
            }
        }

        userToUpdate.setName(updatedUser.getName());
        userToUpdate.setEmail(updatedUser.getEmail());
        return userRepository.save(userToUpdate);
    }

    public User findUserByName(String name) {
        return getAllUsers().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + name));
    }
}
