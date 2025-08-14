package com.test.puntored.user.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 100, message = "Username must be between 4 and 100 characters")
    private String name;

    @NotBlank(message = "Document is required")
    @Size(min = 20, message = "Document must be at least 10 characters")
    private String document;

    @NotBlank(message = "cellphone number is required")
    @Size(min = 10, message = "Phone number must be at least 10 characters")
    private String cellphone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
