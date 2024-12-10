package dev.andrea.jobify.config;

import dev.andrea.jobify.services.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        securityConfig = new SecurityConfig(new CustomUserDetailsService());
    }

    @Test
    public void testUserDetailsService() {
        // Verifica que el UserDetailsService esté correctamente configurado
        UserDetailsService userDetailsService = securityConfig.userDetailsService(new CustomUserDetailsService());
        assertNotNull(userDetailsService, "El UserDetailsService no debería ser nulo");
    }

    @Test
    public void testPasswordEncoder() {
        // Verifica que el PasswordEncoder esté configurado correctamente
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder, "El PasswordEncoder no debería ser nulo");
    }

    @Test
    public void testAuthenticationProvider() {
        // Verifica que el DaoAuthenticationProvider esté configurado correctamente
        DaoAuthenticationProvider authenticationProvider = securityConfig.authenticationProvider();
        assertNotNull(authenticationProvider, "El DaoAuthenticationProvider no debería ser nulo");
    }
}