package com.test.puntored.recharge.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoredBuyResponseDTO {

    @NotBlank(message = "message is required")
    private String message;

    @NotBlank(message = "transactionalID is required")
    private String transactionalID;

    @NotBlank(message = "cellPhone is required")
    private String cellPhone;
    
    @NotBlank(message = "value is required")
    private String value;

  
}