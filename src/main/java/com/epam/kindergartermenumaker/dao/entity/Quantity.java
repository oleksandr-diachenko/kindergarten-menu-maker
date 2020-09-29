package com.epam.kindergartermenumaker.dao.entity;

import lombok.*;

import javax.persistence.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter
    private long id;
    private double amountGross;
    private double amountNet;
}
