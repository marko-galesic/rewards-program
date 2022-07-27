package com.company.rewardspoints.controllers;

import com.company.rewardspoints.model.CustomerPoints;
import com.company.rewardspoints.model.Transaction;
import com.company.rewardspoints.repository.CustomerPointsRepository;
import com.company.rewardspoints.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerPointsRepository customerPointsRepository;

    @PostMapping(produces = {"application/json"})
    private Transaction recordTransaction(@Valid @RequestBody Transaction transaction) {
        Optional<CustomerPoints> customerPointsResult = customerPointsRepository.findById(transaction.getCustomerId());
        CustomerPoints customerPoints;
        Integer points = 0;

        if (transaction.getAmount() > 50 && transaction.getAmount() <= 100) {
            points = transaction.getAmount().intValue() - 50;
        } else if (transaction.getAmount() > 100) {
            points = 2 * (transaction.getAmount().intValue() - 100) + 50;
        }

        if (customerPointsResult.isPresent()) {
            customerPoints = customerPointsResult.get();
            customerPoints.setPoints(customerPoints.getPoints() + points.longValue());
        } else {
            customerPoints = new CustomerPoints(transaction.getCustomerId(), points.longValue());
        }

        customerPointsRepository.save(customerPoints);
        return transactionRepository.save(transaction);
    }
}
