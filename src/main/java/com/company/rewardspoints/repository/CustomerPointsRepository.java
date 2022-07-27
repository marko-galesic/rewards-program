package com.company.rewardspoints.repository;

import com.company.rewardspoints.model.CustomerPoints;
import org.springframework.data.repository.CrudRepository;

public interface CustomerPointsRepository extends CrudRepository<CustomerPoints, Long> {}
