package dev.andrea.jobify.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getAuthenticatedUser() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    @Transactional
    public User createUser(User user) {
        validateUser(user);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        User authenticatedUser = getAuthenticatedUser();
    
        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only view your own account");
        }
    
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User authenticatedUser = getAuthenticatedUser();

        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only update your own account");
        }

        User existingUser = getUserById(userId);

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User authenticatedUser = getAuthenticatedUser();

        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own account");
        }

        User user = getUserById(userId);
        userRepository.delete(user);
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}
