package br.com.pbcompass.demoparkapi.web.dto.mapper;

import br.com.pbcompass.demoparkapi.entity.Parking;
import br.com.pbcompass.demoparkapi.web.dto.parking.ParkingCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.parking.ParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingMapper {

    public static Parking toParking(ParkingCreateDto dto){
        return new ModelMapper().map(dto, Parking.class);
    }

    public static ParkingResponseDto toResponseDto(Parking parking){
        return new ModelMapper().map(parking, ParkingResponseDto.class);
    }


}
