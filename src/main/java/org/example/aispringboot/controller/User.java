package org.example.aispringboot.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aispringboot.DTO.command.UserLoginCommandDTO;
import org.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import org.example.aispringboot.DTO.response.UserLoginResponseDTO;
import org.example.aispringboot.common.Result;
import org.example.aispringboot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/token-info")
    public Result<String> tokenInfo(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");

        return Result.ok(
                "用户：" + claims.getSubject()
                        + "，ID：" + claims.get("userId", Long.class)
        );
    }

    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(
            @Valid @RequestBody UserLoginCommandDTO commandDTO) {

        UserLoginResponseDTO response = userService.login(commandDTO);
        return Result.ok(response);
    }
}
