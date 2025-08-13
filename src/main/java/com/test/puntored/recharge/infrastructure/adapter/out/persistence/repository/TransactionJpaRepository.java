package com.test.puntored.recharge.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.puntored.recharge.infrastructure.adapter.out.persistence.entity.TransactionEntity;

import java.util.UUID;


@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    //Optional<Transaction> findById(String id);
}