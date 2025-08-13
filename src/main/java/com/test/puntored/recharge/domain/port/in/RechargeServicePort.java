package com.test.puntored.recharge.domain.port.in;

import java.util.List;

import com.test.puntored.recharge.application.dto.SupplierDTO;
import com.test.puntored.recharge.application.dto.TransactionRequestDTO;
import com.test.puntored.recharge.application.dto.TransactionResponseDTO;

public interface RechargeServicePort {
   
    TransactionResponseDTO createRecharge(TransactionRequestDTO transactionRequest, String username);
    List<SupplierDTO> getSuppliers();
    List<TransactionResponseDTO> getTransactionsHistory();
    List<TransactionResponseDTO> getTransactionById(String transactionId);
}