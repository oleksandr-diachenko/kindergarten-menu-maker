package com.epam.kindergartermenumaker.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Entity
@Table(name = "recipes")
@Getter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    @NotNull
    private final String name;
    private final String description;
}
