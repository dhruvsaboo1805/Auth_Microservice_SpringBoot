package com.example.Spring.Security.config.security;

import com.example.Spring.Security.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);
        return createToken(claims);
    }

    public String extractEmail(String token) {
        return extractClaims(token).get("email").toString();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            boolean isTokenExpired = isTokenExpired(token);

            boolean usernameMatches = email.equals(userDetails.getUsername());
            return usernameMatches && !isTokenExpired;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims extractClaims(String token) throws JwtException {
        try {
            return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        }
        catch (MalformedJwtException e) {
            throw new JwtException("Invalid MalFormed JWT token");
        }
        catch (ExpiredJwtException e) {
            throw new JwtException("JWT token has expired");
        }
        catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token");
        }
        catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    private SecretKey getSigningKey() {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("email").toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
}
