package org.example.aispringboot.controller;

import jakarta.validation.Valid;
import org.example.aispringboot.DTO.command.UserLoginCommandDTO;
import org.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import org.example.aispringboot.DTO.response.UserLoginResponseDTO;
import org.example.aispringboot.common.Result;
import org.example.aispringboot.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class User {

    private final UserService userService;

    public User(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<String> register(
            @Valid @RequestBody UserRegisterCommandDTO commandDTO) {

        String message = userService.register(commandDTO);
        return Result.ok(message);
    }

    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(
            @Valid @RequestBody UserLoginCommandDTO commandDTO) {

        UserLoginResponseDTO response = userService.login(commandDTO);
        return Result.ok(response);
    }
}
