package dev.andrea.jobify.service;

import dev.andrea.jobify.model.User;
import dev.andrea.jobify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar usuario por correo electrónico
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Asignamos un rol por defecto, por ejemplo, 'ROLE_USER'
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())  // Usamos el correo como nombre de usuario
                .password(user.getPassword())  // La contraseña codificada
                .build();
    }
}
