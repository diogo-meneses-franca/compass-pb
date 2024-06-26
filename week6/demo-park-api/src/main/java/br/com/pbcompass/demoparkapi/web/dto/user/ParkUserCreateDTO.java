package br.com.pbcompass.demoparkapi.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkUserCreateDTO {

    @Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
