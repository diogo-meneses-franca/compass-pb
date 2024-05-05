package br.com.pbcompass.demoparkapi.web.dto.mapper;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.PageableResponseDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkClientMapper {

    public static ParkClient toClient(ParkClientCreateDto dto){
        return new ModelMapper().map(dto, ParkClient.class);
    }

    public static ParkClientResponseDto toDto(ParkClient client){
        return new ModelMapper().map(client, ParkClientResponseDto.class);
    }

    public static PageableResponseDto toDto(Page page){
        return new ModelMapper().map(page, PageableResponseDto.class);
    }
}
