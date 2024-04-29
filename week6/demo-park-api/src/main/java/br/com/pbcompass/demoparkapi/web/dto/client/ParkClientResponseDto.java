package br.com.pbcompass.demoparkapi.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkClientResponseDto {

    private Long id;
    private String name;
    private String cpf;
}
