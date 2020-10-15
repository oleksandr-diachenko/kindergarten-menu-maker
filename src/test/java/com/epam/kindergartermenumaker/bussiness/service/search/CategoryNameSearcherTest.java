package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
class CategoryNameSearcherTest {

    private static final String MAIN_COURSE = "Main course";

    private final Searcher<RecipeIngredient> searcher = new CategoryNameSearcher();

    @Test
    void shouldReturnTrueWhenCategoryNameContainsFilter() {
        Category category = Category.builder().name(MAIN_COURSE).build();
        Recipe recipe = Recipe.builder().category(category).build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();

        boolean actual = searcher.contains(recipeIngredient, "main");

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCategoryNameDoesNotContainsFilter() {
        Category category = Category.builder().name(MAIN_COURSE).build();
        Recipe recipe = Recipe.builder().category(category).build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();

        boolean actual = searcher.contains(recipeIngredient, "qwe");

        assertThat(actual).isFalse();
    }
}