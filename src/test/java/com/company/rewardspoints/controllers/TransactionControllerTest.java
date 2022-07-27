package com.company.rewardspoints.controllers;

import com.company.rewardspoints.model.CustomerPoints;
import com.company.rewardspoints.model.Transaction;
import com.company.rewardspoints.repository.CustomerPointsRepository;
import com.company.rewardspoints.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private CustomerPointsRepository customerPointsRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveTransaction_NewCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Transaction t = new Transaction(123L, 100.0);
        when(customerPointsRepository.findById(123L)).thenReturn(Optional.empty());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        mockMvc.perform(
                post("/transaction")
                    .content(mapper.writeValueAsString(t))
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(
            result -> {
                Transaction r = mapper.readValue(result.getResponse().getContentAsString(), Transaction.class);
                assertEquals(r.getAmount(), t.getAmount());
                assertEquals(r.getCustomerId(), t.getCustomerId());
            }
        );
    }

    @Test
    public void saveTransaction_ExistingCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Transaction t = new Transaction(123L, 100.0);
        Optional<CustomerPoints> customerPointsResult = Optional.of(new CustomerPoints(123L, 50L));
        when(customerPointsRepository.findById(123L)).thenReturn(customerPointsResult);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        mockMvc.perform(
                post("/transaction")
                        .content(mapper.writeValueAsString(t))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(
                result -> {
                    Transaction r = mapper.readValue(result.getResponse().getContentAsString(), Transaction.class);
                    assertEquals(r.getAmount(), t.getAmount());
                    assertEquals(r.getCustomerId(), t.getCustomerId());
                }
        );
    }
}
