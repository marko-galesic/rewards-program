package com.company.rewardspoints.repository;

import com.company.rewardspoints.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository  extends CrudRepository<Transaction, Long> {}
