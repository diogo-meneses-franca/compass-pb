package br.com.pbcompass.demoparkapi.web.dto.mapper;

import br.com.pbcompass.demoparkapi.entity.ParkingSpace;
import br.com.pbcompass.demoparkapi.web.dto.parkingspace.ParkingSpaceCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.parkingspace.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {

    public static ParkingSpace toParkingSpace(ParkingSpaceCreateDto dto){
        return new ModelMapper().map(dto, ParkingSpace.class);
    }

    public static ParkingSpaceResponseDto toResponseDto(ParkingSpace parkingSpace){
        return new ModelMapper().map(parkingSpace, ParkingSpaceResponseDto.class);
    }
}
