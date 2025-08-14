package com.test.puntored.user.application.service;

import com.test.puntored.config.JwtUtil;
import com.test.puntored.user.domain.exception.AuthenticationFailedException;
import com.test.puntored.user.domain.model.User;
import com.test.puntored.user.domain.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor 
public class AuthServiceImpl {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String email, String rawPassword) {
        
          User user = userRepositoryPort.findByEmail(email)
                    .orElseThrow(() -> new AuthenticationFailedException("Usuario o contraseña incorrectos"));

          if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
               throw new AuthenticationFailedException("Usuario o contraseña incorrectos");
          }

          return jwtUtil.generateToken(user.getEmail());
    }

    
}
