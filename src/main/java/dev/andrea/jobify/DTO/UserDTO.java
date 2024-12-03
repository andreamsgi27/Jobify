package dev.andrea.jobify.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String role;

    public UserDTO(User user) {
        this.userId=user.getId();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.role=user.getRole();
    }
}
