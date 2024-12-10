package dev.andrea.jobify.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializar el objeto User antes de cada test
        user = new User(1L, "andrea", "password123", "andrea@example.com");
    }

    @Test
    void testUserConstructor() {
        // Verificar que el objeto User se crea correctamente
        assertEquals("andrea", user.getUsername(), "El nombre de usuario debería ser 'andrea'");
        assertEquals("password123", user.getPassword(), "La contraseña debería ser 'password123'");
        assertEquals("andrea@example.com", user.getEmail(), "El email debería ser 'andrea@example.com'");
    }

    @Test
    void testGetUsername() {
        // Verificar que el método getUsername devuelve el nombre de usuario correcto
        assertEquals("andrea", user.getUsername(), "El nombre de usuario debería ser 'andrea'");
    }

    @Test
    void testGetPassword() {
        // Verificar que el método getPassword devuelve la contraseña correcta
        assertEquals("password123", user.getPassword(), "La contraseña debería ser 'password123'");
    }

    @Test
    void testIsAccountNonExpired() {
        // Verificar que el método isAccountNonExpired devuelve true
        assertTrue(user.isAccountNonExpired(), "La cuenta no debería estar expirada");
    }

    @Test
    void testIsAccountNonLocked() {
        // Verificar que el método isAccountNonLocked devuelve true
        assertTrue(user.isAccountNonLocked(), "La cuenta no debería estar bloqueada");
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Verificar que el método isCredentialsNonExpired devuelve true
        assertTrue(user.isCredentialsNonExpired(), "Las credenciales no deberían haber expirado");
    }

    @Test
    void testIsEnabled() {
        // Verificar que el método isEnabled devuelve true
        assertTrue(user.isEnabled(), "La cuenta debería estar habilitada");
    }

    @Test
    void testBuilder() {
        // Verificar que el patrón Builder funciona correctamente
        User userUsingBuilder = User.builder()
                                    .username("john")
                                    .password("password456")
                                    .email("john@example.com")
                                    .build();

        assertEquals("john", userUsingBuilder.getUsername(), "El nombre de usuario debería ser 'john'");
        assertEquals("password456", userUsingBuilder.getPassword(), "La contraseña debería ser 'password456'");
        assertEquals("john@example.com", userUsingBuilder.getEmail(), "El email debería ser 'john@example.com'");
    }
}