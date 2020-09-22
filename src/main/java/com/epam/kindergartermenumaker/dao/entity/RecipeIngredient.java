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
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Measurement measurement;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Quantity quantity;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;
}
