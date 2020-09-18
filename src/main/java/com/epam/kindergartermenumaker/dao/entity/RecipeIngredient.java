package com.epam.kindergartermenumaker.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Entity
@Table(name = "recipe_ingredients")
@Getter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private final Recipe recipe;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private final Measurement measurement;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private final Quantity quantity;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private final Ingredient ingredient;
}
