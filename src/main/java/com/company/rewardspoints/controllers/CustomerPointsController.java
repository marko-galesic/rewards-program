package com.company.rewardspoints.controllers;

import com.company.rewardspoints.exceptions.ResourceNotFoundException;
import com.company.rewardspoints.model.CustomerPoints;
import com.company.rewardspoints.repository.CustomerPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/customer/{id}/points")
public class CustomerPointsController {
    @Autowired
    private CustomerPointsRepository customerPointsRepository;

    @GetMapping(produces = {"application/json"})
    private CustomerPoints getPointsForCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<CustomerPoints> customerPointsResult = customerPointsRepository.findById(id);
        if (!customerPointsResult.isPresent()) {
            throw new ResourceNotFoundException("Customer not found for id: " + id);
        }
        return customerPointsResult.get();
    }
}