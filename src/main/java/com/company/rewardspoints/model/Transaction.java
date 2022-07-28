package com.company.rewardspoints.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    @Column(nullable = false)
    private Long customerId;

    @NotNull
    @Column(nullable = false)
    private Double amount;
}