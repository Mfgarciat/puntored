package com.test.puntored.user.domain.port.in;


import java.util.Optional;

import com.test.puntored.user.application.dto.UserRegistrationDTO;
import com.test.puntored.user.application.dto.UserResponseDTO;
import com.test.puntored.user.domain.exception.UserAlreadyExistsException;
import com.test.puntored.user.domain.model.User;


public interface UserServicePort {

    UserResponseDTO createUser(UserRegistrationDTO registrationDTO)throws UserAlreadyExistsException;
    Optional<User> findByEmail(String email);  
     
}