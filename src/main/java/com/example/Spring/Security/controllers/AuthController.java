package com.example.Spring.Security.controllers;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.Spring.Security.config.security.JWTUtil;
import com.example.Spring.Security.dto.AuthenticationRequestDTO;
import com.example.Spring.Security.dto.RegisterRequestDTO;
import com.example.Spring.Security.dto.RegisterResponseDTO;
import com.example.Spring.Security.enums.RoleType;
import com.example.Spring.Security.service.AuthService;
import com.example.Spring.Security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerUserDTO) {
        RegisterResponseDTO registerResponseDTO = userService.registerUser(registerUserDTO);
        return ResponseEntity.ok(registerResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequestDTO loginUserDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword())
        );

        UserDetails userDetails = authService.loadUserByUsername(loginUserDTO.getEmail());

        Set<RoleType> roles = userDetails.getAuthorities().stream().map(auth -> RoleType.valueOf(auth.getAuthority())).collect(Collectors.toSet());

        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                roles);

        return ResponseEntity.ok(token);

    }
}
