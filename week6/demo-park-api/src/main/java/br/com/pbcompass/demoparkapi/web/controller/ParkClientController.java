package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.jwt.JwtUserDetails;
import br.com.pbcompass.demoparkapi.service.ParkClientService;
import br.com.pbcompass.demoparkapi.service.ParkUserService;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientResponseDto;
import br.com.pbcompass.demoparkapi.web.dto.mapper.ParkClientMapper;
import br.com.pbcompass.demoparkapi.web.dto.user.ParkUserResponseDTO;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clients")
@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ParkClientController {

    private final ParkClientService clientService;
    private final ParkUserService parkUserService;

    @Operation(
            summary = "Create a new client",
            description = "Resource to create a new client associated with a registered user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "409",
                            description = "Client already exists.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not processed due to invalid entry data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ParkClientResponseDto> save(@RequestBody @Valid ParkClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails){
        ParkClient client = ParkClientMapper.toClient(dto);
        client.setUser(parkUserService.findById(userDetails.getId()));
        clientService.save(client);
        return ResponseEntity.status(201).body(ParkClientMapper.toDto(client));

    }
}
