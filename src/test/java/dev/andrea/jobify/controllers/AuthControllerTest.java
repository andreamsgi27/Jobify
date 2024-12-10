package dev.andrea.jobify.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.andrea.jobify.DTOs.UserDTO;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john_doe", "password123", "john@example.com");
        userDTO = new UserDTO(user);
    }

    @Test
    void registerUser_ShouldReturnUserDTO() throws Exception {
        // Mockear la creación del usuario
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Configurar el MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        // Simular la petición al endpoint de registro
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john_doe\", \"password\":\"password123\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }
}