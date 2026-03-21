package com.example.Spring.Security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponseDTO {
    private String email;
    List<String> roles;
    private boolean isValid;
}
