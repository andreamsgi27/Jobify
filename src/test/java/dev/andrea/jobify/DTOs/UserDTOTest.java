package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import dev.andrea.jobify.models.User;

class UserDTOTest {

    @Test
    void constructorShouldMapFieldsCorrectly() {
        User user = mock(User.class);
        
        when(user.getUserId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("john_doe");
        when(user.getEmail()).thenReturn("johndoe@example.com");

        UserDTO userDTO = new UserDTO(user);

        assertEquals(1L, userDTO.getUserId());
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
    }

    @Test
    void constructorWithParametersShouldMapFieldsCorrectly() {
        UserDTO userDTO = new UserDTO(1L, "john_doe", "johndoe@example.com");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
    }
}
