package br.com.pbcompass.demoparkapi.web.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkUserResponseDTO {

    private Long id;
    private String username;
    private String role;

}
