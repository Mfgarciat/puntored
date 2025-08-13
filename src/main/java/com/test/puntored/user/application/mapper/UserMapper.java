package com.test.puntored.user.application.mapper;

import org.springframework.stereotype.Component;

import com.test.puntored.user.application.dto.UserRegistrationDTO;
import com.test.puntored.user.application.dto.UserResponseDTO;
import com.test.puntored.user.domain.model.User;

@Component
public class UserMapper {
    public User toEntity(UserRegistrationDTO requestDTO) {
        return User.builder()
                .name(requestDTO.getName())
                .document(requestDTO.getDocument())
                .phoneNumber(requestDTO.getCellphone())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .build();
    }
    
    public UserResponseDTO toResponseDTO(User entity) {
        return UserResponseDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .build();
    }
}
