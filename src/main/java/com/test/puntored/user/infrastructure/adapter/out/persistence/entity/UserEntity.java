package com.test.puntored.user.infrastructure.adapter.out.persistence.entity;

import java.util.UUID;

import com.test.puntored.user.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String document;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false, length = 100, unique = true) 
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Boolean active; 

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
            .id(user.getId() != null ? UUID.fromString(user.getId()) : null)
            .name(user.getName())
            .document(user.getDocument())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .password(user.getPassword())
            .active(true)
            .build();
    }

    public User toDomain() {
        return User.builder()
            .id(this.id != null ? this.id.toString() : null)
            .name(this.name)
            .document(this.document)
            .email(this.email)
            .phoneNumber(this.phoneNumber)
            .password(this.password)
            .active(this.active)
            .build();
    }
}
