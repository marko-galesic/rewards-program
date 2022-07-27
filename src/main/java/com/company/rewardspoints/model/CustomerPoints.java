package com.company.rewardspoints.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerPoints {
    @Id
    private Long id;

    @Column
    @NotNull
    private Long points;
}