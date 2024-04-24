package br.com.pbcompass.demoparkapi.web.controller;

import br.com.pbcompass.demoparkapi.entity.User;
import br.com.pbcompass.demoparkapi.service.UserService;
import br.com.pbcompass.demoparkapi.web.dto.UserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.UserPasswordDTO;
import br.com.pbcompass.demoparkapi.web.dto.UserResponseDTO;
import br.com.pbcompass.demoparkapi.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateDTO userCreateDTO) {
        User mappedUser = UserMapper.toUser(userCreateDTO);
        User response = userService.save(mappedUser);
        UserResponseDTO mappedResponse = UserMapper.toUserResponseDTO(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User response = userService.findById(id);
        UserResponseDTO mappedResponse = UserMapper.toUserResponseDTO(response);
        return ResponseEntity.ok().body(mappedResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
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
