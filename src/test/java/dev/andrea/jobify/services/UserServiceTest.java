package dev.andrea.jobify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.UserRepository;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test123");
        user.setEmail("test@example.com");

        when(passwordEncoder.encode("test123")). thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("test", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(passwordEncoder).encode("test123");
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_updatesAndSavesUser() {
        // Simular el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar usuario autenticado en el repositorio
        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        authenticatedUser.setPassword("encodedPassword");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        // Configurar usuarios existentes y datos actualizados
        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");
        existingUser.setEmail("test@example.com");

        User updatedDetails = new User();
        updatedDetails.setUsername("newUsername");
        updatedDetails.setPassword("newPassword");
        updatedDetails.setEmail("newEmail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Ejecutar el método
        User updatedUser = userService.updateUser(1L, updatedDetails);

        // Verificar los resultados
        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("encodedNewPassword", updatedUser.getPassword());
        assertEquals("newEmail@example.com", updatedUser.getEmail());
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(existingUser);

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
    }

    @Test
    void listUsers_returnsListOfUsers() {
        // Crear datos simulados
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("user2");

        List<User> mockUsers = Arrays.asList(user1, user2);

        // Configurar el comportamiento del mock
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Llamar al método a probar
        List<User> result = userService.listUsers();

        // Verificar resultados
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        // Verificar que el repositorio fue llamado una vez
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_returnsUserForAuthenticatedUser() {
        // Simular el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar el usuario autenticado
        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        // Configurar el usuario buscado
        User fetchedUser = new User();
        fetchedUser.setUserId(1L);
        fetchedUser.setUsername("authenticatedUsername");
        fetchedUser.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(fetchedUser));

        // Ejecutar el método
        User result = userService.getUserById(1L);

        // Verificar los resultados
        assertEquals(1L, result.getUserId());
        assertEquals("authenticatedUsername", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findById(1L);

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_deletesUserWhenAuthorized() {
        // Simular el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar el usuario autenticado
        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        // Configurar el usuario a eliminar
        User userToDelete = new User();
        userToDelete.setUserId(1L);
        userToDelete.setUsername("authenticatedUsername");
        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));

        // Ejecutar el método
        userService.deleteUser(1L);

        // Verificar que el usuario fue eliminado
        verify(userRepository).delete(userToDelete);

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_throwsExceptionWhenUnauthorized() {
        // Simular el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar el usuario autenticado
        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        // Ejecutar el método y verificar la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(2L));
        assertEquals("Unauthorized: You can only delete your own account", exception.getMessage());

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_throwsExceptionWhenUserNotFound() {
        // Simular el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar el usuario autenticado
        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        // Configurar usuario no encontrado
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar la excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
    }

    @Test
    void validateUser_throwsExceptionWhenUsernameIsEmpty() throws Exception {
        // Crear un usuario con nombre de usuario vacío
        User user = new User();
        user.setUsername("");
        user.setPassword("validPassword");

        // Usar reflexión para acceder al método privado
        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        // Verificar que lanza IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                validateUserMethod.invoke(userService, user);
            } catch (InvocationTargetException e) {
                throw (IllegalArgumentException) e.getCause(); // Lanza la causa original
            }
        });
        assertEquals("Username cannot be empty", exception.getMessage());
    }

    @Test
    void validateUser_throwsExceptionWhenPasswordIsEmpty() throws Exception {
        // Crear un usuario con contraseña vacía
        User user = new User();
        user.setUsername("validUsername");
        user.setPassword("");

        // Usar reflexión para acceder al método privado
        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        // Verificar que lanza IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                validateUserMethod.invoke(userService, user);
            } catch (InvocationTargetException e) {
                throw (IllegalArgumentException) e.getCause(); // Lanza la causa original
            }
        });
        assertEquals("Password cannot be empty", exception.getMessage());
    }


    @Test
    void validateUser_doesNotThrowExceptionForValidUser() throws Exception {
        // Crear un usuario válido
        User user = new User();
        user.setUsername("validUsername");
        user.setPassword("validPassword");

        // Usar reflexión para acceder al método privado
        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        // Verificar que no lanza ninguna excepción
        validateUserMethod.invoke(userService, user);
    }
}