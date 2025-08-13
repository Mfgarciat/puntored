package com.test.puntored.recharge.infrastructure.adapter.out.persistence.entity;

import com.test.puntored.recharge.domain.model.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double value;

    @Column(nullable = false, length = 4)
    private String supplierId;

    @Column(nullable = false, length = 36)
    private String transactionalId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant date;

    public static TransactionEntity fromDomain(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId() != null ? UUID.fromString(transaction.getId()) : null)
                .phoneNumber(transaction.getPhoneNumber())
                .value(transaction.getValue())
                .supplierId(transaction.getSupplierId())
                .transactionalId(transaction.getTransactionalId())
                .date(transaction.getDate())
                .build();
    }

    public Transaction toDomain() {
        return Transaction.builder()
                .id(this.id.toString())
                .phoneNumber(this.phoneNumber)
                .value(this.value)
                .supplierId(this.supplierId)
                .transactionalId(this.transactionalId)
                .date(this.date)
                .build();
    }

}