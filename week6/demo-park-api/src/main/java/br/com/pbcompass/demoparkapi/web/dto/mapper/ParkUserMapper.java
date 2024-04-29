package br.com.pbcompass.demoparkapi.web.dto.mapper;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import br.com.pbcompass.demoparkapi.web.dto.user.ParkUserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.user.ParkUserResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkUserMapper {

    public static ParkUser toUser(ParkUserCreateDTO parkUserCreateDTO) {
        return new ModelMapper().map(parkUserCreateDTO, ParkUser.class);
    }

    public static ParkUserResponseDTO toUserResponseDTO(ParkUser user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<ParkUser, ParkUserResponseDTO> props = new PropertyMap<ParkUser, ParkUserResponseDTO>() {
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, ParkUserResponseDTO.class);
    }
}
