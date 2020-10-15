package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
class IngredientNameSearcherTest {

    private final Searcher<RecipeIngredient> searcher = new IngredientNameSearcher();

    @Test
    void shouldReturnTrueWhenIngredientNameContainsFilter() {
        Ingredient ingredient = Ingredient.builder().name("Milk").build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().ingredient(ingredient).build();

        boolean actual = searcher.contains(recipeIngredient, "mi");

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenIngredientNameDoesNotContainsFilter() {
        Ingredient ingredient = Ingredient.builder().name("Milk").build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().ingredient(ingredient).build();

        boolean actual = searcher.contains(recipeIngredient, "qwe");

        assertThat(actual).isFalse();
    }
}