package com.test.puntored.recharge.application.mapper;

import org.springframework.stereotype.Component;

import com.test.puntored.recharge.application.dto.TransactionRequestDTO;
import com.test.puntored.recharge.application.dto.TransactionResponseDTO;
import com.test.puntored.recharge.domain.model.Transaction;

@Component
public class TransactionMapper {
    public Transaction toEntity(TransactionRequestDTO requestDTO, String username) {
        return Transaction.builder()
                .supplierId(requestDTO.getSupplierId())
                .value(requestDTO.getValue())
                .phoneNumber(requestDTO.getPhoneNumber())
                .build();
    }
    
    public TransactionResponseDTO toResponseDTO(Transaction entity) {
        return TransactionResponseDTO.builder()
            .id(entity.getId())
            .date(entity.getDate())
            .value(entity.getValue())
            .phoneNumber(entity.getPhoneNumber())
            .supplierId(entity.getSupplierId())
            .transactionalId(entity.getTransactionalId())
            .build();
    }
}
