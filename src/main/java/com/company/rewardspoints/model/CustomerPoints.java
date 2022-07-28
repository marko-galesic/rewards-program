package com.company.rewardspoints.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "customerpoints")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerPoints {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long customerId;

    @NotNull
    @Column
    private Date date;

    @Column
    @NotNull
    private Long points;
}