package com.example.Spring.Security.controllers;

import com.example.Spring.Security.dto.AuthenticationRequestDTO;
import com.example.Spring.Security.dto.AuthenticationResponseDTO;
import com.example.Spring.Security.dto.RegisterRequestDTO;
import com.example.Spring.Security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponseDTO> register( @Valid @RequestBody RegisterRequestDTO dto) {
//        return ResponseEntity.ok(authService.regsiter(dto));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponseDTO> login( @Valid @RequestBody AuthenticationRequestDTO dto) {
//        return ResponseEntity.ok(authService.login(dto));
//    }
}
