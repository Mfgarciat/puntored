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
public class SupplierDTO {

    @NotBlank(message = "id is required")
    private String id;
    
    @NotBlank(message = "name is required")
    private String name;
}
