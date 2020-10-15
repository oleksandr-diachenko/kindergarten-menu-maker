package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
class RecipeNameSearcherTest {

    private final Searcher<RecipeIngredient> searcher = new RecipeNameSearcher();

    @Test
    void shouldReturnTrueWhenRecipeNameContainsFilter() {
        Recipe recipe = Recipe.builder().name("Fried potatoes").build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();

        boolean actual = searcher.contains(recipeIngredient, "tat");

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenRecipeNameDoesNotContainsFilter() {
        Recipe recipe = Recipe.builder().name("Fried potatoes").build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();

        boolean actual = searcher.contains(recipeIngredient, "qwe");

        assertThat(actual).isFalse();
    }
}