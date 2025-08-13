package com.test.puntored.user.infrastructure.adapter.in.controllers;

import com.test.puntored.config.JwtAuthenticationFilter;
import com.test.puntored.config.JwtUtil;
import com.test.puntored.user.application.dto.LoginRequestDTO;
import com.test.puntored.user.application.service.AuthServiceImpl;
import com.test.puntored.user.infrastructure.adapter.in.controller.AuthController;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private AuthServiceImpl authService;

        @MockBean
        private JwtUtil jwtUtil; // Mock del JwtUtil faltante

        @MockBean
        private JwtAuthenticationFilter jwtAuthenticationFilter; // Mock del filtro

        @Test
        void login_Success() throws Exception {
                // Arrange
                LoginRequestDTO request = LoginRequestDTO.builder()
                                .email("test@example.com")
                                .password("123456789")
                                .build();

                String mockToken = "mockToken123";

                when(authService.login(anyString(), anyString())).thenReturn(mockToken);

                // Act & Assert
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(mockToken));
        }

        @Test
        void login_Failure() throws Exception {
                // Arrange
                LoginRequestDTO request = LoginRequestDTO.builder()
                        .email("wrong@example.com")
                        .password("wrongpass")
                        .build();

                when(authService.login(anyString(), anyString()))
                                .thenThrow(new RuntimeException("Invalid credentials"));

                // Act & Assert
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isUnauthorized())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("Authentication failed: Invalid credentials"));
        }
}