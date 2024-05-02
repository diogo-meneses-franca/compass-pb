package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.ParkingSpace;
import br.com.pbcompass.demoparkapi.service.ParkingSpaceService;
import br.com.pbcompass.demoparkapi.web.dto.mapper.ParkingSpaceMapper;
import br.com.pbcompass.demoparkapi.web.dto.parkingspace.ParkingSpaceCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.parkingspace.ParkingSpaceResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid ParkingSpaceCreateDto dto){
        ParkingSpace parkingSpace = ParkingSpaceMapper.toParkingSpace(dto);
        parkingSpaceService.save(parkingSpace);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpace.getCode())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> findByCode(@PathVariable String code){
        ParkingSpace parkingSpace = parkingSpaceService.findByCode(code);
        ParkingSpaceResponseDto responseDto = ParkingSpaceMapper.toResponseDto(parkingSpace);
        return ResponseEntity.ok().body(responseDto);

    }
}
