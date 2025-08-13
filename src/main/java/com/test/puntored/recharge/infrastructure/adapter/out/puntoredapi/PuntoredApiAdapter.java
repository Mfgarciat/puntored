package com.test.puntored.recharge.infrastructure.adapter.out.puntoredapi;

import com.test.puntored.recharge.application.dto.PuntoredBuyResponseDTO;
import com.test.puntored.recharge.domain.model.Supplier;
import com.test.puntored.recharge.domain.model.Transaction;
import com.test.puntored.recharge.domain.port.out.PuntoredApiPort;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import java.util.List;
import java.util.Map;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;


@Component
public class PuntoredApiAdapter implements PuntoredApiPort {

    private String authToken;
    private final RestTemplate restTemplate;
    
    @Value("${puntored.api.url}")
    private String API_BASE_URL;

    @Value("${puntored.api.key}")
    private String API_KEY;
    
    @Value("${puntored.auth.user}")
    private String AUTH_USER;
    
    @Value("${puntored.auth.password}")
    private String AUTH_PASSWORD;

    public PuntoredApiAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String authenticate() {
        String url = API_BASE_URL + "/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "mtrQF6Q11eosqyQnkMY0JGFbGqcxVg5icvfVnX1ifIyWDvwGApJ8WUM8nHVrdSkN");
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> authRequest = new HashMap<>();
        authRequest.put("user", AUTH_USER);
        authRequest.put("password", AUTH_PASSWORD);
               
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(authRequest, headers);
        
        try {
            ResponseEntity<AuthResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, AuthResponse.class);
            if (response.getBody() != null) {
                this.authToken = response.getBody().getToken();
                return this.authToken;
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Error en la autenticación: " + e.getStatusCode());
            System.err.println("Respuesta del servidor: " + e.getResponseBodyAsString());
        }catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public List<Supplier> getSuppliers(String token) {
        String url = API_BASE_URL + "/getSuppliers";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", token);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<Supplier[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Supplier[].class);
            
            // --- LOG DE DEPURACIÓN ---
            System.out.println("Status Code: " + response.getStatusCode());
            if (response.getBody() == null || response.getBody().length == 0) {
                System.out.println("Respuesta del body de la API de Puntored es nula o vacía.");
            } else {
                System.out.println("Proveedores obtenidos: " + response.getBody().length);
            }
            // --- FIN DEL LOG ---

            if (response.getBody() != null) {
                return List.of(response.getBody());
            }
        } catch (Exception e) {
            // --- LOG DE ERRORES ---
            e.printStackTrace();
            // --- FIN DEL LOG ---
        }
        return Collections.emptyList();
    }

    @Override
    public Transaction buy(Transaction transaction, String token) {
        String url = API_BASE_URL + "/buy";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> buyRequest = new HashMap<>();
        buyRequest.put("cellPhone", transaction.getPhoneNumber());
        buyRequest.put("value", transaction.getValue());
        buyRequest.put("supplierId", transaction.getSupplierId());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(buyRequest, headers);

        try {
            ResponseEntity<PuntoredBuyResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, PuntoredBuyResponseDTO.class);

            if (response.getBody() != null) {
                PuntoredBuyResponseDTO apiResponse = response.getBody();
                Transaction tx = new Transaction();
                tx.setPhoneNumber(apiResponse.getCellPhone());
                tx.setValue(Double.valueOf(apiResponse.getValue()));
                tx.setSupplierId(transaction.getSupplierId());
                tx.setTransactionalId(apiResponse.getTransactionalID()); 
                tx.setDate(Instant.now());
                return tx;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Clases auxiliares para el mapeo JSON
    private static class AuthResponse {
        private String token;
        public String getToken() { return token; }
        //public void setToken(String token) { this.token = token; }
    }
    
   
}