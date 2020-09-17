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
@Table(name = "recipe_ingredients")
@Data
@Builder
@EqualsAndHashCode(exclude = "id")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;
    @ManyToOne(fetch = FetchType.LAZY)
    private Measurement measurement;
    @ManyToOne(fetch = FetchType.LAZY)
    private Quantity quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;
}
