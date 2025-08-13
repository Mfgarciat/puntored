package com.test.puntored.user.infrastructure.adapter.in.controller;

import com.test.puntored.user.application.dto.LoginRequestDTO;
import com.test.puntored.user.application.dto.LoginResponseDTO;
import com.test.puntored.user.application.service.AuthServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        try {
            String token = authService.login(requestDTO.getEmail(), requestDTO.getPassword());
            LoginResponseDTO response = new LoginResponseDTO(token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Authentication failed: " + e.getMessage()));
        }
    }
}
