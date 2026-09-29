package org.example.aispringboot.service;

import org.example.aispringboot.DTO.command.UserLoginCommandDTO;
import org.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import org.example.aispringboot.DTO.response.UserLoginResponseDTO;

public interface UserService {

    String register(UserRegisterCommandDTO commandDTO);

    UserLoginResponseDTO login(UserLoginCommandDTO commandDTO);
}
