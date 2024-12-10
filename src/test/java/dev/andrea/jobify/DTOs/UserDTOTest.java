package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import dev.andrea.jobify.models.User;

class UserDTOTest {

    @Test
    void constructorShouldMapFieldsCorrectly() {
        // Arrange: Crear un objeto mockeado de User
        User user = mock(User.class);
        
        // Definir comportamientos del mock
        when(user.getUserId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("john_doe");
        when(user.getEmail()).thenReturn("johndoe@example.com");

        // Act: Crear un UserDTO usando el constructor que toma un User
        UserDTO userDTO = new UserDTO(user);

        // Assert: Verificar que los campos se asignaron correctamente
        assertEquals(1L, userDTO.getUserId());
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
    }

    @Test
    void constructorWithParametersShouldMapFieldsCorrectly() {
        // Act: Crear un UserDTO utilizando el constructor con parámetros
        UserDTO userDTO = new UserDTO(1L, "john_doe", "johndoe@example.com");

        // Assert: Verificar que los campos se asignaron correctamente
        assertEquals(1L, userDTO.getUserId());
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
    }
}
