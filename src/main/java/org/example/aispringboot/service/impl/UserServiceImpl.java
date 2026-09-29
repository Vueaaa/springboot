package org.example.aispringboot.service.impl;

import org.example.aispringboot.DTO.command.UserLoginCommandDTO;
import org.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import org.example.aispringboot.common.BusinessException;
import org.example.aispringboot.common.ResultCode;
import org.example.aispringboot.entity.UserEntity;
import org.example.aispringboot.repository.UserRepository;
import org.example.aispringboot.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.aispringboot.DTO.response.UserLoginResponseDTO;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(UserRegisterCommandDTO commandDTO) {
        if (userRepository.findByUsername(commandDTO.getUsername()).isPresent()) {
            throw new BusinessException(ResultCode.ACCOUNT_SAME);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(commandDTO.getUsername());

        String encodedPassword =
                passwordEncoder.encode(commandDTO.getPassword());
        userEntity.setPassword(encodedPassword);

        userRepository.save(userEntity);

        return "注册成功";
    }

    @Override
    public UserLoginResponseDTO login(UserLoginCommandDTO commandDTO) {
        UserEntity userEntity = userRepository
                .findByUsername(commandDTO.getUsername())
                .orElseThrow(() -> new BusinessException(
                        ResultCode.LOGIN_FAILED
                ));

        boolean passwordMatches = passwordEncoder.matches(
                commandDTO.getPassword(),
                userEntity.getPassword()
        );

        if (!passwordMatches) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }

        String temporaryToken = "token-for-user-" + userEntity.getId();

        return new UserLoginResponseDTO(temporaryToken);
    }
}
