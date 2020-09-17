package com.epam.kindergartermenumaker.dao.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @OneToOne(fetch = FetchType.LAZY)
    private final Recipe recipe;
    @OneToOne(fetch = FetchType.LAZY)
    private final Measurement measurement;
    @OneToOne(fetch = FetchType.LAZY)
    private final Quantity quantity;
    @OneToOne(fetch = FetchType.LAZY)
    private final Ingredient ingredient;
}
