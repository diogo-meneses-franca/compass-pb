package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.jwt.JwtUserDetails;
import br.com.pbcompass.demoparkapi.repository.projection.ParkClientProjection;
import br.com.pbcompass.demoparkapi.service.ParkClientService;
import br.com.pbcompass.demoparkapi.service.ParkUserService;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientPageableDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientResponseDto;
import br.com.pbcompass.demoparkapi.web.dto.mapper.ParkClientMapper;
import br.com.pbcompass.demoparkapi.web.dto.user.ParkUserResponseDTO;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Clients")
@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ParkClientController {

    private final ParkClientService clientService;
    private final ParkUserService parkUserService;
    private final ParkClientService parkClientService;

    @Operation(
            summary = "Create a new client",
            description = "If the user is authenticated creates his client register",
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

    @Operation(
            summary = "Search for a client by his id",
            description = "By default, returns the required client without it's related user account. Requires ADMIN privileges to access this resource",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkClientResponseDto> findById(@PathVariable Long id){
        ParkClient client = parkClientService.findById(id);
        ParkClientResponseDto mappedClient = ParkClientMapper.toDto(client);
        return ResponseEntity.ok().body(mappedClient);
    }

    @Operation(
            summary = "Retrieves all registered clients",
            description = "By default returns a paginated item containing all registered clients ordered by name, without it's related user accounts data.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(
                            in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "The returned page"
                    ),
                    @Parameter(
                            in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "The total of elements per page"
                    ),
                    @Parameter(
                            in = QUERY, name = "sort",
                            hidden = true,
                            content = @Content(schema = @Schema(type = "string", defaultValue = "id/asc")),
                            description = "Accepts multiple criteria to order elements on the page"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkClientPageableDto> findAll(@Parameter(hidden = true) @PageableDefault(size = 20, sort = {"name"}) Pageable pageable) {
        Page<ParkClientProjection> clientPage = parkClientService.findAll(pageable);
        ParkClientPageableDto parkClientPageableDto = ParkClientMapper.toDto(clientPage);
        return ResponseEntity.ok().body(parkClientPageableDto);
    }

}
