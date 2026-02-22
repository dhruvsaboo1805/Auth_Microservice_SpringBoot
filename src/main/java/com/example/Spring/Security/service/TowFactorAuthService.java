package com.example.Spring.Security.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TowFactorAuthService {

    private final GoogleAuthenticator googleAuthenticator;

    public TowFactorAuthService() {
        this.googleAuthenticator = new GoogleAuthenticator();
    }

    public GoogleAuthenticatorKey generateSecret() {
        return googleAuthenticator.createCredentials();
    }

    public boolean verifyCode(String secret , int code) {
        return googleAuthenticator.authorize(secret, code);
    }

    public String getQrCodeUrl(String email, GoogleAuthenticatorKey key) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(
                "MyApp",
                email,
                key
        );
    }


}
