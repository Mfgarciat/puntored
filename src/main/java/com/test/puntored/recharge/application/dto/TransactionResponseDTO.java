package com.test.puntored.recharge.application.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                
@Builder
@NoArgsConstructor    
@AllArgsConstructor    
public class TransactionResponseDTO {
    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "transactionalId is required")
    private String transactionalId;

    @NotBlank(message = "value is required")
    private Double value;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotBlank(message = "SupplierId is required")
    private String supplierId;

    @NotBlank(message = "Date ID is required")
    private Instant date;
}
