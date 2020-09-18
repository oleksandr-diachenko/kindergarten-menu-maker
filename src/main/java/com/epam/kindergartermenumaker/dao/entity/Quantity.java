package com.epam.kindergartermenumaker.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Entity
@Table(name = "measurement_qty")
@Getter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    @Min(1)
    private final int amount;
}
