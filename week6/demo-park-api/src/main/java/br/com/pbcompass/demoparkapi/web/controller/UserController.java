package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.User;
import br.com.pbcompass.demoparkapi.service.UserService;
import br.com.pbcompass.demoparkapi.web.dto.UserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.UserPasswordDTO;
import br.com.pbcompass.demoparkapi.web.dto.UserResponseDTO;
import br.com.pbcompass.demoparkapi.web.dto.mapper.UserMapper;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Users", description = "Contains all operations related to resources for registering, editing and reading users.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Resource to create a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "409",
                            description = "User already exists.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not processed, invalid entry data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User mappedUser = UserMapper.toUser(userCreateDTO);
        User response = userService.save(mappedUser);
        UserResponseDTO mappedResponse = UserMapper.toUserResponseDTO(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedResponse);
    }

    @Operation(
            summary = "Search a user by id",
            description = "Resource to search a user by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User response = userService.findById(id);
        UserResponseDTO mappedResponse = UserMapper.toUserResponseDTO(response);
        return ResponseEntity.ok().body(mappedResponse);
    }

    @Operation(
            summary = "Update password",
            description = "Resource to update a user password through it's id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
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
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(id, userPasswordDTO.getCurrentPassword(), userPasswordDTO.getNewPassword(), userPasswordDTO.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<User> response = userService.findAll();
        List<UserResponseDTO> mappedList = response.stream().map(UserMapper::toUserResponseDTO).toList();
        return ResponseEntity.ok().body(mappedList);
    }
}
