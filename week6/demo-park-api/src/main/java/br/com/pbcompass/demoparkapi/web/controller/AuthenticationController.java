package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.jwt.JwtToken;
import br.com.pbcompass.demoparkapi.jwt.JwtUserDetailsService;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserLoginDto;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserResponseDTO;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@Tag(name = "Authentication", description = "Resource to proceed authentication on the API")
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtUserDetailsService service;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Authenticate to the API",
            description = "Resource to authenticate to the API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success on authentication and the return of a Bearer token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkUserResponseDTO.class))),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid credentials",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not processed, invalid entry data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            } )
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid ParkUserLoginDto dto, HttpServletRequest request) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = service.getJwtTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e){
            log.warn("Bad credentials for username {}", dto.getUsername());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credentials"));
    }
}
