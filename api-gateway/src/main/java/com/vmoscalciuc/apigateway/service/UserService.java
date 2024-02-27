package com.vmoscalciuc.apigateway.service;

import com.vmoscalciuc.apigateway.config.security.JwtUtil;
import com.vmoscalciuc.apigateway.dto.LoginResponse;
import com.vmoscalciuc.apigateway.dto.RegistrationRequest;
import com.vmoscalciuc.apigateway.dto.RegistrationResponse;
import com.vmoscalciuc.apigateway.enums.Role;
import com.vmoscalciuc.apigateway.exception.AuthenticationException;
import com.vmoscalciuc.apigateway.exception.UserAlreadyExistsException;
import com.vmoscalciuc.apigateway.model.UserEntity;
import com.vmoscalciuc.apigateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }
        UserEntity newUser = UserEntity.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(Role.ROLE_USER)
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .mobile(registrationRequest.getMobile())
                .email(registrationRequest.getEmail())
                .enabled(true)
                .build();
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser);
        RegistrationResponse response = modelMapper.map(newUser, RegistrationResponse.class);
        response.setToken(token);

        return response;
    }

    public LoginResponse authenticateUser(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            LoginResponse response = modelMapper.map(userEntity, LoginResponse.class);
            String token = jwtUtil.generateToken(userEntity);
            response.setToken(token);
            return response;
        } else {
            throw new AuthenticationException("Cannot auth, username or password are incorrect");
        }
    }
}