package com.test.puntored.recharge.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data         
@Builder         
@NoArgsConstructor      
@AllArgsConstructor    
public class TransactionRequestDTO {
    
    @Size(min = 4, max = 4, message = "The Supplier ID must have exactly 4 digits")
    @NotBlank(message = "Supplier ID is required")
    private String supplierId;

    @Min(value = 1000, message = "The minimum vlaue is $ 1.000")
    @Max(value = 100000, message = "The maximun vlaue  es de $ 100.000")
    private Double value;

    @Pattern(regexp = "^3\\d{9}$", message = "The phoneNumber must start with '3', have 10 digits, and contain only numbers")
    @Size(min = 10, max = 10, message = "The phoneNumber must have exactly 10 digits")
    private String phoneNumber;
}
