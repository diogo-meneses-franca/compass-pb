package br.com.pbcompass.demoparkapi.web.dto.parkingspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpaceResponseDto {

    private Long id;
    private String code;
    private String status;
}
