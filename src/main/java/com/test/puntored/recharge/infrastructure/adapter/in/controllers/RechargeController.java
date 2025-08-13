package com.test.puntored.recharge.infrastructure.adapter.in.controllers;

import com.test.puntored.recharge.application.dto.SupplierDTO;
import com.test.puntored.recharge.application.dto.TransactionRequestDTO;
import com.test.puntored.recharge.application.dto.TransactionResponseDTO;
import com.test.puntored.recharge.domain.port.in.RechargeServicePort;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recharge")
public class RechargeController {

    @Autowired
    private RechargeServicePort rechargeServicePort;

    @PostMapping("/buy")
    public ResponseEntity<TransactionResponseDTO> createRecharge(@RequestBody @Valid TransactionRequestDTO requestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            TransactionResponseDTO responseDTO = rechargeServicePort.createRecharge(requestDTO, username);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierDTO>> getSuppliers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          
        List<SupplierDTO> supplierDTOs = rechargeServicePort.getSuppliers();
        return ResponseEntity.ok(supplierDTOs);
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        List<TransactionResponseDTO> responseDTOs = rechargeServicePort.getTransactionsHistory();
        return ResponseEntity.ok(responseDTOs);
    }
}