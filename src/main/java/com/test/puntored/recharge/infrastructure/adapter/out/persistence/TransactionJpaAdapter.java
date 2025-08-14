package com.test.puntored.recharge.infrastructure.adapter.out.persistence;

import com.test.puntored.recharge.domain.model.Transaction;
import com.test.puntored.recharge.domain.port.out.TransactionRepositoryPort;
import com.test.puntored.recharge.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import com.test.puntored.recharge.infrastructure.adapter.out.persistence.repository.TransactionJpaRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionJpaAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
       
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        TransactionEntity savedEntity = transactionJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public List<Transaction> findAll() {
        List<TransactionEntity> entities = transactionJpaRepository.findAll();
        return entities.stream().map(TransactionEntity::toDomain).collect(Collectors.toList());
    }
 
}