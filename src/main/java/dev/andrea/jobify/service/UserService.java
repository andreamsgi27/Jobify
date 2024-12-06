package dev.andrea.jobify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.andrea.jobify.model.User;
import dev.andrea.jobify.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener el usuario autenticado
    public User getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    // Crear un nuevo usuario
    @Transactional
    public User createUser(User user) {
        // Validación de campos vacíos
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Codificar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Listar todos los usuarios
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por su ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    // Actualizar un usuario
    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Si la contraseña fue proporcionada, se actualiza (con codificación)
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        // Actualizar otros campos como el nombre o el email si es necesario
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        return userRepository.save(existingUser);
    }

    // Eliminar un usuario
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // Eliminar el usuario si existe
        userRepository.delete(user);
    }
}
