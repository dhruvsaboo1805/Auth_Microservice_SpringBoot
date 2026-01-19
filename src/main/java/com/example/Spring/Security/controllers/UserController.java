package com.example.Spring.Security.controllers;

import com.example.Spring.Security.entity.User;
import com.example.Spring.Security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getUserProfile() {
        log.info("Fetching user profile");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        log.info("Fetching user profile for user: {}", email);

        User user = userService.findByEmail(email);
        log.info("User profile fetched successfully for user: {}", user.getEmail());
        return ResponseEntity.ok(user);
    }

}