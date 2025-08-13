package com.test.puntored.user.application.service;

import com.test.puntored.user.domain.exception.UserAlreadyExistsException;

import com.test.puntored.user.application.dto.UserRegistrationDTO;
import com.test.puntored.user.application.dto.UserResponseDTO;
import com.test.puntored.user.application.mapper.UserMapper;
import com.test.puntored.user.domain.model.User;
import com.test.puntored.user.domain.port.in.UserServicePort;
import com.test.puntored.user.domain.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserResponseDTO createUser(UserRegistrationDTO userDTO) throws UserAlreadyExistsException {
        
        User user = userMapper.toEntity(userDTO);
        if (userRepositoryPort.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }
      
        user.setPassword(passwordEncoder.encode(user.getPassword()));        
        User saveUser =  userRepositoryPort.createUser(user);
        return userMapper.toResponseDTO(saveUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryPort.findByEmail(email);
    }

}

   