package dev.andrea.jobify.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    // Implementar todos los métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; //Devolvería los roles
    }

    @Override
    public String getUsername() {
        return this.email; // Devuelve el correo electrónico como identificador, cambiar luego si se desea autorizar con el username
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambiar
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambiar
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambiar
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambiar
    }
}