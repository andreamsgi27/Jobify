package dev.andrea.jobify.DTO;

import org.apache.catalina.User;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO {
    private Long appId;
    private User userId;
    private String company;
    private String position;
    private String requirements;
    private Type type;

    public ApplicationDTO(Long appId, Long userId) {
        this.appId = appId;
        this.userId=application.getUser().getId();

        }
    
}
