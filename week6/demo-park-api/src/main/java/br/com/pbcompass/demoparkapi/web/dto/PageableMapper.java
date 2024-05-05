package br.com.pbcompass.demoparkapi.web.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableResponseDto toDto(Page page){
        return new ModelMapper().map(page, PageableResponseDto.class);
    }
}
