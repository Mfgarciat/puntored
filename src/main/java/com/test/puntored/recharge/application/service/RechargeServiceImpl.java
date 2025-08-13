package com.test.puntored.recharge.application.service;

import com.test.puntored.recharge.domain.model.Transaction;
import com.test.puntored.recharge.domain.port.in.RechargeServicePort;
import com.test.puntored.recharge.domain.port.out.PuntoredApiPort;
import com.test.puntored.recharge.domain.port.out.TransactionRepositoryPort;

import lombok.RequiredArgsConstructor;

import com.test.puntored.recharge.application.dto.SupplierDTO;
import com.test.puntored.recharge.application.dto.TransactionRequestDTO;
import com.test.puntored.recharge.application.dto.TransactionResponseDTO;
import com.test.puntored.recharge.application.mapper.TransactionMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor 
public class RechargeServiceImpl implements RechargeServicePort {

    private final PuntoredApiPort puntoredApiPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDTO createRecharge(TransactionRequestDTO requestDTO, String username) {
        Transaction transaction = transactionMapper.toEntity(requestDTO, username);
        String token = puntoredApiPort.authenticate();
        Transaction processedTransaction = puntoredApiPort.buy(transaction, token);
        Transaction savedTransaction = transactionRepositoryPort.save(processedTransaction);
        return transactionMapper.toResponseDTO(savedTransaction);
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        String token = puntoredApiPort.authenticate();
        return puntoredApiPort.getSuppliers(token).stream()
                .map(supplier -> SupplierDTO.builder()
                        .id(supplier.getId())
                        .name(supplier.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsHistory() {
        return transactionRepositoryPort.findAll().stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

  @Override
    public List<TransactionResponseDTO> getTransactionById(String transactionId) {
        /*return transactionRepositoryPort.findById(transactionId)
                .stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());*/
         return transactionRepositoryPort.findAll().stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}