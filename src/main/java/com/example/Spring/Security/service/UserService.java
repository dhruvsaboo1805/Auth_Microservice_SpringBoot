package com.example.Spring.Security.service;

import com.example.Spring.Security.dto.RegisterRequestDTO;
import com.example.Spring.Security.dto.RegisterResponseDTO;
import com.example.Spring.Security.entity.Role;
import com.example.Spring.Security.entity.User;
import com.example.Spring.Security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new UsernameNotFoundException("Email Already Exists Go to Login Window.....");
        }
        User newUser = new User();
        newUser.setEmail(registerRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        newUser.setRoles(Set.of(Role.USER));
        userRepository.save(newUser);
        log.info("New User: {}", newUser);

        return RegisterResponseDTO.builder().email(newUser.getEmail()).password(newUser.getPassword()).build();

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
