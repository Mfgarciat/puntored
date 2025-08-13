package com.test.puntored.user.infrastructure.adapter.in.controllers;

import com.test.puntored.config.JwtUtil;
import com.test.puntored.config.JwtAuthenticationFilter;
import com.test.puntored.user.application.dto.UserRegistrationDTO;
import com.test.puntored.user.application.dto.UserResponseDTO;
import com.test.puntored.user.application.service.UserServiceImpl;
import com.test.puntored.user.domain.exception.UserAlreadyExistsException;
import com.test.puntored.user.infrastructure.adapter.in.controller.UserController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UserServiceImpl userServicePort;

        @MockBean
        private JwtUtil jwtUtil; // Mock del JwtUtil faltante

        @MockBean
        private JwtAuthenticationFilter jwtAuthenticationFilter; // Mock del filtro

        @Test
        public void registerUser_shouldReturn200AndUserResponse_whenRegistrationIsSuccessful() throws Exception {
                // Preparar datos de prueba
                UserRegistrationDTO registrationDTO = UserRegistrationDTO.builder()
                                .name("testuser")
                                .document("1258745678")
                                .cellphone("3001234567")
                                .email("test@example.com")
                                .password("123456789")
                                .build();

                UserResponseDTO responseDTO = UserResponseDTO.builder()
                                .id(null)
                                .name("testuser")
                                .email("test@example.com")
                                .build();

                // Configurar mock
                when(userServicePort.createUser(any(UserRegistrationDTO.class))).thenReturn(responseDTO);

                // Ejecutar y verificar
                mockMvc.perform(post("/api/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registrationDTO)))
                                .andExpect(status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testuser"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
        }

        @Test
        public void registerUser_shouldReturnConflict_whenUserAlreadyExists() throws Exception {

                UserRegistrationDTO registrationDTO = UserRegistrationDTO.builder()
                                .name("existinguser")
                                .document("1000745678")
                                .cellphone("3005825678")
                                .email("existing@example.com")
                                .password("Test@123")
                                .build();

                when(userServicePort.createUser(any(UserRegistrationDTO.class)))
                                .thenThrow(new UserAlreadyExistsException("User already exists"));

                mockMvc.perform(post("/api/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registrationDTO)))
                                .andExpect(status().isConflict());
        }

        @Test
        public void registerUser_shouldReturn400_whenInputIsInvalid() throws Exception {
                UserRegistrationDTO invalidDTO = new UserRegistrationDTO(); // Datos faltantes

                mockMvc.perform(post("/api/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void registerUser_shouldReturn500_whenUnexpectedErrorOccurs() throws Exception {
                UserRegistrationDTO registrationDTO = UserRegistrationDTO.builder()
                                .name("existinguser")
                                .document("1000745678")
                                .cellphone("3005825678")
                                .email("existing@example.com")
                                .password("Test@123")
                                .build();

                when(userServicePort.createUser(any(UserRegistrationDTO.class)))
                                .thenThrow(new RuntimeException("Unexpected error"));

                mockMvc.perform(post("/api/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registrationDTO)))
                                .andExpect(status().isInternalServerError());
        }
}