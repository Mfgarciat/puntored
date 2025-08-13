package com.test.puntored.user.infrastructure.adapter.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.puntored.user.application.dto.UserRegistrationDTO;
import com.test.puntored.user.application.dto.UserResponseDTO;
import com.test.puntored.user.domain.exception.UserAlreadyExistsException;
import com.test.puntored.user.domain.port.in.UserServicePort;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private  UserServicePort userServicePort;


    @PostMapping("/registration")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegistrationDTO registrationDTO)  throws UserAlreadyExistsException{
        try {
            UserResponseDTO user = userServicePort.createUser(registrationDTO);
            return ResponseEntity.ok(user);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

}
