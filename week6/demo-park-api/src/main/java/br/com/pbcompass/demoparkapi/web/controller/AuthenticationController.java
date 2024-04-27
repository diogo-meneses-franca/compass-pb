package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.jwt.JwtToken;
import br.com.pbcompass.demoparkapi.jwt.JwtUserDetailsService;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserLoginDto;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtUserDetailsService service;
    private final AuthenticationManager authenticationManager;

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
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credentials"))
    }
}
