package com.test.puntored.user.domain.port.out;

import java.util.Optional;

import com.test.puntored.user.domain.model.User;

public interface UserRepositoryPort {

    User createUser(User user) ;  
    Optional<User> findByEmail(String email);     
}
