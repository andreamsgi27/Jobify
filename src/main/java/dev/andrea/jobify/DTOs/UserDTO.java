package dev.andrea.jobify.DTOs;

import dev.andrea.jobify.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
}