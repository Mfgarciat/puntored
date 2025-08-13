package com.test.puntored.recharge.domain.port.out;

import com.test.puntored.recharge.domain.model.Supplier;
import com.test.puntored.recharge.domain.model.Transaction;
import java.util.List;

public interface PuntoredApiPort {

    String authenticate();
    List<Supplier> getSuppliers(String token);
    Transaction buy(Transaction transaction, String token);
}