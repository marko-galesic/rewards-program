package com.company.rewardspoints.controllers;

import com.company.rewardspoints.exceptions.TransactionParseException;
import com.company.rewardspoints.model.CustomerPoints;
import com.company.rewardspoints.model.Report;
import com.company.rewardspoints.model.Transaction;
import com.company.rewardspoints.repository.CustomerPointsRepository;
import com.company.rewardspoints.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/submit-transactions")
class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerPointsRepository customerPointsRepository;
    private static final
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {"application/json"})
    private List<Report> recordTransaction(@RequestParam("file") MultipartFile file, @PathParam("total") Boolean total) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<Transaction> transactions = new ArrayList<>();
        List<CustomerPoints> customerPoints = new ArrayList<>();
        Transaction t = new Transaction();
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
            .lines().forEach(
                s -> {
                    String[] parts = s.split(",");
                    Date d;
                    if (parts.length != 3) {
                        throw new TransactionParseException("Line doesn't have date, customer id, and transaction amount: " + s);
                    }
                    try {
                        d = simpleDateFormat.parse(parts[0]);
                    } catch (ParseException e) {
                        throw new TransactionParseException("Unable to parse date: " + parts[0]);
                    }
                    try {
                        t.setCustomerId(Long.parseLong(parts[1]));
                    } catch(NumberFormatException e) {
                        throw new TransactionParseException("Unable to parse customer id: " + parts[1]);
                    }
                    try {
                        t.setAmount(Double.parseDouble(parts[2]));
                    } catch (NumberFormatException | NullPointerException e) {
                        throw new TransactionParseException("Unable to parse transaction amount: " + parts[2]);
                    }

                    t.setDate(d);
                    Integer points = 0;

                    if (t.getAmount() > 50 && t.getAmount() <= 100) {
                        points = t.getAmount().intValue() - 50;
                    } else if (t.getAmount() > 100) {
                        points = 2 * (t.getAmount().intValue() - 100) + 50;
                    }
                    customerPoints.add(new CustomerPoints(null, t.getCustomerId(), d, points.longValue()));
                    transactions.add(t);
                }
            );

        customerPointsRepository.saveAll(customerPoints);
        transactionRepository.saveAll(transactions);
        if (total) {
            return customerPointsRepository.getTotalPointsForAllCustomers();
        }
        return customerPointsRepository.getTotalPointsForAllCustomersPerMonth();
    }
}
