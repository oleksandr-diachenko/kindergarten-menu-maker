package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
class CategoryNameSearcherTest {

    private final Searcher<RecipeIngredient> searcher = new CategoryNameSearcher();

    @Test
    void shouldReturnTrueWhenCategoryNameContainsFilter() {
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();

        boolean actual = searcher.contains(recipeIngredient, "main");

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCategoryNameDoesNotContainsFilter() {
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();

        boolean actual = searcher.contains(recipeIngredient, "qwe");

        assertThat(actual).isFalse();
    }
}