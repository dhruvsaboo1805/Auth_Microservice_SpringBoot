package com.example.Spring.Security.controllers;

import com.example.Spring.Security.config.security.JWTUtil;
import com.example.Spring.Security.dto.*;
import com.example.Spring.Security.entity.User;
import com.example.Spring.Security.repository.UserRepository;
import com.example.Spring.Security.service.AuthService;
import com.example.Spring.Security.service.TowFactorAuthService;
import com.example.Spring.Security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TowFactorAuthService twoFactorAuthService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerUserDTO) {
        RegisterResponseDTO registerResponseDTO = userService.registerUser(registerUserDTO);
        return ResponseEntity.ok(registerResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody AuthenticationRequestDTO loginUserDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isTwoFactorEnabled()) {
            return ResponseEntity.ok(
                    AuthenticationResponseDTO.builder()
                            .status("OTP_REQUIRED")
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build()
            );
        }

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("User LoggedIn Successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean valid = twoFactorAuthService.verifyCode(
                user.getTwoFactorSecret(),
                request.getOtp()
        );

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid OTP");
        }

        UserDetails userDetails = authService.loadUserByUsername(user.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(
                AuthenticationResponseDTO.builder()
                        .email(userDetails.getUsername())
                        .password(userDetails.getPassword())
                        .token(token)
                        .type("Bearer")
                        .status("SUCCESS")
                        .build()
        );
    }



}
