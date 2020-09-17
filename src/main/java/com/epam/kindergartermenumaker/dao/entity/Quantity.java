package com.epam.kindergartermenumaker.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Entity
@Table(name = "measurement_qty")
@Data
@Builder
@EqualsAndHashCode(exclude = "id")
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int amount;
}
