package com.epam.kindergartermenumaker.dao.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Entity
@Table(name = "recipes")
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    private final String name;
    private final String description;
}
