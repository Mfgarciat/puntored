package com.test.puntored.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "email is required")
    private String email;
   
    @NotBlank(message = "password is required")
    private String password;
}
