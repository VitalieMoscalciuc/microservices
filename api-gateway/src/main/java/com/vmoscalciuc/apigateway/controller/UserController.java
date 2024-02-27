package com.vmoscalciuc.apigateway.controller;

import com.vmoscalciuc.apigateway.config.security.AuthenticationManager;
import com.vmoscalciuc.apigateway.config.security.JwtUtil;
import com.vmoscalciuc.apigateway.dto.LoginRequest;
import com.vmoscalciuc.apigateway.dto.LoginResponse;
import com.vmoscalciuc.apigateway.dto.RegistrationRequest;
import com.vmoscalciuc.apigateway.dto.RegistrationResponse;
import com.vmoscalciuc.apigateway.model.UserEntity;
import com.vmoscalciuc.apigateway.service.UserDetailsServiceImpl;
import com.vmoscalciuc.apigateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    // Register a new user
    @PostMapping("/register")
    public Mono<ResponseEntity<RegistrationResponse>> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationResponse response =  userService.registerUser(registrationRequest);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    // Login endpoint
    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> loginUser(@RequestBody LoginRequest loginUser) {
        LoginResponse loginResponse = userService
                .authenticateUser(loginUser.getUsername(), loginUser.getPassword());

        return Mono.just(ResponseEntity.ok().body(loginResponse));
    }

}
