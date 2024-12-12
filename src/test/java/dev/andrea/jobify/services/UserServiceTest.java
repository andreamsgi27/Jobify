package dev.andrea.jobify.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        authenticatedUser.setPassword("encodedPassword");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

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

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("encodedNewPassword", updatedUser.getPassword());
        assertEquals("newEmail@example.com", updatedUser.getEmail());
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(existingUser);

        SecurityContextHolder.clearContext();
    }

    @Test
    void listUsers_returnsListOfUsers() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("user2");

        List<User> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.listUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_returnsUserForAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        User fetchedUser = new User();
        fetchedUser.setUserId(1L);
        fetchedUser.setUsername("authenticatedUsername");
        fetchedUser.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(fetchedUser));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getUserId());
        assertEquals("authenticatedUsername", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findById(1L);

        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_deletesUserWhenAuthorized() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        User userToDelete = new User();
        userToDelete.setUserId(1L);
        userToDelete.setUsername("authenticatedUsername");
        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));

        userService.deleteUser(1L);

        verify(userRepository).delete(userToDelete);

        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_throwsExceptionWhenUnauthorized() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(2L));
        assertEquals("Unauthorized: You can only delete your own account", exception.getMessage());

        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteUser_throwsExceptionWhenUserNotFound() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUsername");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User authenticatedUser = new User();
        authenticatedUser.setUserId(1L);
        authenticatedUser.setUsername("authenticatedUsername");
        when(userRepository.findByUsername("authenticatedUsername")).thenReturn(Optional.of(authenticatedUser));

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());

        SecurityContextHolder.clearContext();
    }

    @Test
    void validateUser_throwsExceptionWhenUsernameIsEmpty() throws Exception {
        User user = new User();
        user.setUsername("");
        user.setPassword("validPassword");

        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                validateUserMethod.invoke(userService, user);
            } catch (InvocationTargetException e) {
                throw (IllegalArgumentException) e.getCause();
            }
        });
        assertEquals("Username cannot be empty", exception.getMessage());
    }

    @Test
    void validateUser_throwsExceptionWhenPasswordIsEmpty() throws Exception {
        User user = new User();
        user.setUsername("validUsername");
        user.setPassword("");

        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                validateUserMethod.invoke(userService, user);
            } catch (InvocationTargetException e) {
                throw (IllegalArgumentException) e.getCause();
            }
        });
        assertEquals("Password cannot be empty", exception.getMessage());
    }


    @Test
    void validateUser_doesNotThrowExceptionForValidUser() throws Exception {
        User user = new User();
        user.setUsername("validUsername");
        user.setPassword("validPassword");

        Method validateUserMethod = UserService.class.getDeclaredMethod("validateUser", User.class);
        validateUserMethod.setAccessible(true);

        validateUserMethod.invoke(userService, user);
    }
}