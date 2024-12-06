package dev.andrea.jobify.config;

import dev.andrea.jobify.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/register").permitAll()  // Permitir registro para todos
                                .requestMatchers("/user/**").authenticated()    // Requiere autenticación para /user
                                .requestMatchers("/application/**").authenticated()  // Proteger endpoints de aplicaciones
                                .requestMatchers("/app/phase/**").authenticated() // Proteger endpoints de fase
                                .anyRequest().denyAll()  // Denegar todas las demás solicitudes por defecto
                );
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailsService customUserDetailsService) {
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
