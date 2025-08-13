package com.test.puntored.recharge.domain.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private String id;
    private String phoneNumber;
    private double value;
    private String supplierId;
    private String transactionalId;
    private Instant date;
}