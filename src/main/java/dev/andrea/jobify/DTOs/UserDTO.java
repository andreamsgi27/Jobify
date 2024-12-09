package dev.andrea.jobify.DTOs;

import dev.andrea.jobify.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    // Constructor que convierte el User en UserDTO
    public UserDTO (User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public UserDTO(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}