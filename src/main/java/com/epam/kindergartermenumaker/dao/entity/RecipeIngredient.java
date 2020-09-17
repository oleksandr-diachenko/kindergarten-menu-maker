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
@Table(name = "recipe_ingredients")
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Recipe recipe;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Measurement measurement;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Quantity quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Ingredient ingredient;
}
