package com.company.rewardspoints.repository;

import com.company.rewardspoints.model.CustomerPoints;

import com.company.rewardspoints.model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerPointsRepository extends CrudRepository<CustomerPoints, Long> {
    @Query("select new com.company.rewardspoints.model.Report(cp.customerId, sum(cp.points)) from customerpoints cp group by cp.customerId")
    List<Report> getTotalPointsForAllCustomers();
    @Query("select new com.company.rewardspoints.model.Report(DATE_TRUNC('MONTH', cp.date), cp.customerId, sum(cp.points)) from customerpoints cp group by DATE_TRUNC('MONTH', cp.date), cp.customerId")
    List<Report> getTotalPointsForAllCustomersPerMonth();
}
