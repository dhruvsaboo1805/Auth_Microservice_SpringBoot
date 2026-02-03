package com.example.Spring.Security.dto;

import com.example.Spring.Security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String username;
    private String password;
    private String email;
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private Set<Role> roles;
}
