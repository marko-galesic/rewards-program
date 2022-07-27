package com.company.rewardspoints.controllers;

import com.company.rewardspoints.model.CustomerPoints;
import com.company.rewardspoints.model.Transaction;
import com.company.rewardspoints.repository.CustomerPointsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerPointsController.class)
public class CustomerPointsControllerTest {
    @MockBean
    private CustomerPointsRepository customerPointsRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPoints() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CustomerPoints customerPoints = new CustomerPoints(123L, 50L);
        Optional<CustomerPoints> customerPointsResult = Optional.of(customerPoints);
        when(customerPointsRepository.findById(123L)).thenReturn(customerPointsResult);

        mockMvc.perform(
                get("/customer/123/points")
                        .content(mapper.writeValueAsString(customerPoints))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(
                result -> {
                    CustomerPoints r = mapper.readValue(result.getResponse().getContentAsString(), CustomerPoints.class);
                    assertEquals(r.id(), customerPoints.id());
                    assertEquals(r.points(), customerPoints.points());
                }
        );
    }
}
