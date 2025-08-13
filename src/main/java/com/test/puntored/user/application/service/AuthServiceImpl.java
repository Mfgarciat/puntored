package com.test.puntored.user.application.service;

import com.test.puntored.config.JwtUtil;
import com.test.puntored.user.domain.exception.AuthenticationFailedException;
import com.test.puntored.user.domain.model.User;
import com.test.puntored.user.domain.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;


@Service
@RequiredArgsConstructor 
public class AuthServiceImpl {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String login(String email, String rawPassword) {
        
          User user = userRepositoryPort.findByEmail(email)
                    .orElseThrow(() -> new AuthenticationFailedException("Usuario o contraseña incorrectos"));

          if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
               throw new AuthenticationFailedException("Usuario o contraseña incorrectos");
          }

          return jwtUtil.generateToken(user.getEmail());
    }


    
    // public String login(String email, String password) {
    //      try {
            
    //         Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(email, password));
                      
    //         return jwtUtil.generateToken(authentication.getName());
            
    //     } catch (org.springframework.security.core.AuthenticationException e) {
    //         throw new AuthenticationFailedException("Error de autenticación: " + e.getMessage());
    //     }
    // }
    
}
