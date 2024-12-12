package dev.andrea.jobify.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "andrea", "password123", "andrea@example.com");
    }

    @Test
    void testUserConstructor() {
        assertEquals("andrea", user.getUsername(), "El nombre de usuario debería ser 'andrea'");
        assertEquals("password123", user.getPassword(), "La contraseña debería ser 'password123'");
        assertEquals("andrea@example.com", user.getEmail(), "El email debería ser 'andrea@example.com'");
    }

    @Test
    void testGetUsername() {
        assertEquals("andrea", user.getUsername(), "El nombre de usuario debería ser 'andrea'");
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", user.getPassword(), "La contraseña debería ser 'password123'");
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired(), "La cuenta no debería estar expirada");
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked(), "La cuenta no debería estar bloqueada");
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Verificar que el método isCredentialsNonExpired devuelve true
        assertTrue(user.isCredentialsNonExpired(), "Las credenciales no deberían haber expirado");
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled(), "La cuenta debería estar habilitada");
    }

    @Test
    void testBuilder() {
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