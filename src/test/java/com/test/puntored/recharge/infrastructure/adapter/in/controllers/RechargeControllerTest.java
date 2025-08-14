package com.test.puntored.recharge.infrastructure.adapter.in.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.puntored.config.JwtAuthenticationFilter;
import com.test.puntored.config.JwtUtil;
import com.test.puntored.recharge.application.dto.SupplierDTO;
import com.test.puntored.recharge.application.dto.TransactionRequestDTO;
import com.test.puntored.recharge.application.dto.TransactionResponseDTO;
import com.test.puntored.recharge.domain.port.in.RechargeServicePort;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = RechargeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@AutoConfigureMockMvc(addFilters = false)
class RechargeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private RechargeServicePort rechargeServicePort;

        @MockBean
        private JwtUtil jwtUtil; 

        @MockBean
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        private static final String MOCK_USERNAME = "testuser";
        private static final String MOCK_TOKEN = "mock-jwt-token";

        @Test
        @WithMockUser(username = MOCK_USERNAME)
        void createRecharge_shouldReturnCreated_onSuccess() throws Exception {
                
                TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                                .phoneNumber("3001234567")
                                .value(5000.00)
                                .supplierId("8753")
                                .build();

                TransactionResponseDTO transactionFromDB = TransactionResponseDTO.builder()
                                .id("0a335f9e-245c-4c36-a5f5-9053418743ae")
                                .transactionalId("52c399e0-0635-43f3-9735-0711bda77b10")
                                .value(5000.00)
                                .phoneNumber("3001234567")
                                .supplierId("8753")
                                .date(Instant.parse("2025-08-10T16:20:35.407468Z"))
                                .build();
               
                when(rechargeServicePort.createRecharge(any(TransactionRequestDTO.class), any(String.class)))
                                .thenReturn(transactionFromDB);

               
                mockMvc.perform(post("/api/recharge/buy")
                                .header("Authorization", "Bearer " + MOCK_TOKEN)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO)))
                                .andDo(print()) // Agrega esta línea para ver la respuesta completa en caso de fallo
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.value").value(transactionFromDB.getValue()))
                                .andExpect(jsonPath("$.phoneNumber").value(transactionFromDB.getPhoneNumber()));
        }

        @Test
        @WithMockUser(username = MOCK_USERNAME)
        void createRecharge_shouldReturnBadRequest_onIllegalArgumentException()
                        throws Exception {
                
                TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                                .phoneNumber("3001234567")
                                .value(5000.00)
                                .supplierId("1")
                                .build();

                when(rechargeServicePort.createRecharge(any(TransactionRequestDTO.class), eq(MOCK_USERNAME)))
                                .thenThrow(new IllegalArgumentException("Invalid data"));

                
                mockMvc.perform(post("/api/recharge/buy")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO)))
                                .andExpect(status().isBadRequest());
        }

       

        @Test
        @WithMockUser(username = MOCK_USERNAME)
        void getSuppliers_shouldReturnOkAndSuppliersList() throws Exception {
                
                List<SupplierDTO> suppliers = Collections.singletonList(new SupplierDTO("1", "Claro"));
                when(rechargeServicePort.getSuppliers()).thenReturn(suppliers);

               
                mockMvc.perform(get("/api/recharge/suppliers")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("Claro"));
        }

        
        @Test
        @WithMockUser(username = MOCK_USERNAME)
        void getTransactionsHistory_shouldReturnOkAndHistoryList() throws Exception {
               

                TransactionResponseDTO responseDTO = TransactionResponseDTO.builder()
                                .id("ab745bfg-245c-4c36-a5f5-9053418743ae")
                                .transactionalId("ab745bfg-0635-43f3-9735-0711bda77b10")
                                .value(2000.00)
                                .phoneNumber("3001234567")
                                .supplierId("1875")
                                .date(Instant.parse("2025-08-10T16:20:35.407468Z"))
                                .build();
                

                List<TransactionResponseDTO> history = Collections.singletonList(responseDTO);
                
                when(rechargeServicePort.getTransactionsHistory()).thenReturn(history);

                
                mockMvc.perform(get("/api/recharge/history")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].phoneNumber").value("3001234567"));
        }

}