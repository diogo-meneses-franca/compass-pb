package br.com.pbcompass.demoparkapi.web.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponseDto {

    private String plate;
    private String brand;
    private String model;
    private String color;
    private String clientCpf;
    private String invoice;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private String parkingSpaceCode;
    private BigDecimal value;
    private BigDecimal discount;
}
