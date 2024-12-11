package dev.andrea.jobify.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.UserRepository;

@ExtendWith(MockitoExtension.class) // Agregar esta anotación para que Mockito se encargue de la inicialización de los mocks
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;  // El mock de UserRepository

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;  // El servicio que se va a testear

    private User user;

    @BeforeEach
    void setUp() {
        // Crear un usuario de ejemplo
        user = new User();
        user.setUsername("test@example.com");
        user.setPassword("password");
    }

    @Test
    void loadUserByUsername_ShouldReturnUser_WhenUserExists() {
        // Simular que el repositorio devuelve el usuario
        when(userRepository.findByUsername("test@example.com")).thenReturn(java.util.Optional.of(user));

        // Llamar al método y verificar que devuelve el usuario correctamente
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        // Verificar que el usuario devuelto es el mismo que el que simulamos
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        // Simular que el repositorio no encuentra el usuario
        when(userRepository.findByUsername("test@example.com")).thenReturn(java.util.Optional.empty());

        // Llamar al método y verificar que lanza la excepción UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("test@example.com");
        });
    }
}