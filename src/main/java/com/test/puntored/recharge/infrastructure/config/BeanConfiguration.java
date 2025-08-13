package com.test.puntored.recharge.infrastructure.config;

import com.test.puntored.recharge.application.mapper.TransactionMapper;
import com.test.puntored.recharge.application.service.RechargeServiceImpl;
import com.test.puntored.recharge.domain.port.in.RechargeServicePort;
import com.test.puntored.recharge.domain.port.out.PuntoredApiPort;
import com.test.puntored.recharge.domain.port.out.TransactionRepositoryPort;
import com.test.puntored.recharge.infrastructure.adapter.out.persistence.TransactionJpaAdapter;
import com.test.puntored.recharge.infrastructure.adapter.out.persistence.repository.TransactionJpaRepository;
import com.test.puntored.recharge.infrastructure.adapter.out.puntoredapi.PuntoredApiAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PuntoredApiPort puntoredApiPort(RestTemplate restTemplate) {
        return new PuntoredApiAdapter(restTemplate);
    }
    
    @Bean
    public TransactionRepositoryPort transactionRepositoryPort(TransactionJpaRepository jpaTransactionRepository) {
        return new TransactionJpaAdapter(jpaTransactionRepository);
    }

     @Bean
    public RechargeServicePort rechargeServicePort(PuntoredApiPort puntoredApiPort,
                                                 TransactionRepositoryPort transactionRepositoryPort,
                                                 TransactionMapper transactionMapper) {
        return new RechargeServiceImpl(puntoredApiPort, transactionRepositoryPort, transactionMapper);
    }
}