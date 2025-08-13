package com.test.puntored.recharge.domain.port.out;

import com.test.puntored.recharge.domain.model.Transaction;
import java.util.List;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);
    List<Transaction> findAll();
}