package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import br.com.pbcompass.demoparkapi.service.ParkUserService;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserPasswordDTO;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserResponseDTO;
import br.com.pbcompass.demoparkapi.web.dto.mapper.ParkUserMapper;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Users", description = "Contains all operations related to resources for registering, editing and reading users.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class ParkUserController {

    private final ParkUserService parkUserService;

    @Operation(
            summary = "Create a new user",
            description = "Resource to create a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "409",
                            description = "User already exists.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not processed, invalid entry data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @PostMapping
    public ResponseEntity<ParkUserResponseDTO> create(@Valid @RequestBody ParkUserCreateDTO parkUserCreateDTO) {
        ParkUser mappedUser = ParkUserMapper.toUser(parkUserCreateDTO);
        ParkUser response = parkUserService.save(mappedUser);
        ParkUserResponseDTO mappedResponse = ParkUserMapper.toUserResponseDTO(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedResponse);
    }

    @Operation(
            summary = "Search a user by id",
            description = "Request requires a Bearer token. Access allowed to ADMIN or CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<ParkUserResponseDTO> findById(@PathVariable Long id) {
        ParkUser response = parkUserService.findById(id);
        ParkUserResponseDTO mappedResponse = ParkUserMapper.toUserResponseDTO(response);
        return ResponseEntity.ok().body(mappedResponse);
    }

    @Operation(
            summary = "Update password",
            description = "Request requires a Bearer token. Access allowed to ADMIN or CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Passwords don't match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Wrong current password",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody ParkUserPasswordDTO parkUserPasswordDTO) {
        parkUserService.updatePassword(id, parkUserPasswordDTO.getCurrentPassword(), parkUserPasswordDTO.getNewPassword(), parkUserPasswordDTO.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lists all registered users",
            description = "Request requires a Bearer token. Access allowed to ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                @ApiResponse(responseCode = "200", description = "List all users",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = ParkUserResponseDTO.class)))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User without permissions to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ParkUserResponseDTO>> findAll() {
        List<ParkUser> response = parkUserService.findAll();
        List<ParkUserResponseDTO> mappedList = response.stream().map(ParkUserMapper::toUserResponseDTO).toList();
        return ResponseEntity.ok().body(mappedList);
    }
}
