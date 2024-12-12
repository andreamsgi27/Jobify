package dev.andrea.jobify.controllers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;

import dev.andrea.jobify.DTOs.UserDTO;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe", "password123", "johndoe@example.com");
        userDTO = new UserDTO(user);
    }

    @Test
    void listUsers_shouldReturnListOfUserDTOs() {
        List<User> users = Arrays.asList(user, new User(2L, "Jane Doe", "password123", "janedoe@example.com"));
        when(userService.listUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.listUsers();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getUsername());
        assertEquals("Jane Doe", response.getBody().get(1).getUsername());
    }

    @Test
    void getUserById_shouldReturnUserDTO() {
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDTO() {
        User updatedUser = new User(1L, "John Smith", "password123", "johnsmith@example.com");
        UserDTO updatedUserDTO = new UserDTO(updatedUser);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, updatedUser);

        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedUserDTO, response.getBody());
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }
}

