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

    // Obtener el usuario autenticado
    public User getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    // Crear un nuevo usuario
    @Transactional
    public User createUser(User user) {
        validateUser(user);

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Codificar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Listar todos los usuarios, metodo aun no autorizado para nadie
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por su ID
    public User getUserById(Long userId) {
        User authenticatedUser = getAuthenticatedUser();
    
        // Verificar que el usuario autenticado tiene el mismo ID que el ID solicitado
        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only view your own account");
        }
    
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    // Actualizar un usuario
    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User authenticatedUser = getAuthenticatedUser();

        // Verificar que el usuario autenticado tiene el mismo ID que el usuario a actualizar
        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only update your own account");
        }

        User existingUser = getUserById(userId);

        // Si la contraseña fue proporcionada, se actualiza (con codificación)
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Actualizar otros campos
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        return userRepository.save(existingUser);
    }

    // Eliminar un usuario
    @Transactional
    public void deleteUser(Long userId) {
        User authenticatedUser = getAuthenticatedUser();

        // Verificar que el usuario autenticado tiene el mismo ID que el usuario a eliminar
        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own account");
        }

        User user = getUserById(userId);
        userRepository.delete(user);
    }

    // Método privado para validar un usuario
    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}
