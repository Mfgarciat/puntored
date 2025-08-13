package com.test.puntored.user.infrastructure.adapter.out.persistence;

import com.test.puntored.user.domain.model.User;
import com.test.puntored.user.domain.port.out.UserRepositoryPort;
import com.test.puntored.user.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.test.puntored.user.infrastructure.adapter.out.persistence.repository.UserJpaRepository;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserJpaAdapter( UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User createUser(User user) {
       UserEntity entity = UserEntity.fromDomain(user);
       entity.setActive(true);
       UserEntity savedEntity = userJpaRepository.save(entity);
       return savedEntity.toDomain();
       
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }



   
}