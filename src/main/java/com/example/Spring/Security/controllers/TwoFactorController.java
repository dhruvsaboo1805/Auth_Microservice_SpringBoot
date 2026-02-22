package com.example.Spring.Security.controllers;

import com.example.Spring.Security.dto.TwoFactorSetupResponse;
import com.example.Spring.Security.entity.User;
import com.example.Spring.Security.repository.UserRepository;
import com.example.Spring.Security.service.TowFactorAuthService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/2fa")
@RequiredArgsConstructor
public class TwoFactorController {

    private final TowFactorAuthService twoFactorAuthService;
    private final UserRepository userRepository;

    // Enable 2FA
    @PostMapping("/enable")
    public ResponseEntity<?> enable2FA(@RequestParam String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GoogleAuthenticatorKey key = twoFactorAuthService.generateSecret();

        user.setTwoFactorSecret(key.getKey());
        user.setTwoFactorEnabled(true);
        userRepository.save(user);

        String qrUrl = twoFactorAuthService.getQrCodeUrl(email, key);

        new TwoFactorSetupResponse();
        TwoFactorSetupResponse twoFactorSetupResponse = TwoFactorSetupResponse
                .builder()
                .secret(key.getKey())
                .qrCodeUrl(qrUrl)
                .build();

        return ResponseEntity.ok(twoFactorSetupResponse);
    }
}

