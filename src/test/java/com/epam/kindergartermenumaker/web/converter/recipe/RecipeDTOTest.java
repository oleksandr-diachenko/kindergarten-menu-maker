package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/8/2020
 **/
class RecipeDTOTest {

    @Test
    void shouldReturnSameWhenNameIsEquals() {
        Recipe a = Recipe.builder().name("A").build();
        Recipe b = Recipe.builder().name("A").build();
        RecipeDTO recipeDTO1 = RecipeDTO.builder().recipe(a).build();
        RecipeDTO recipeDTO2 = RecipeDTO.builder().recipe(b).build();

        int compareTo = recipeDTO1.compareTo(recipeDTO2);

        assertThat(compareTo).isZero();
    }

    @Test
    void shouldReturnFirstWhenFirstNameLetterIsFirst() {
        Recipe a = Recipe.builder().name("A").build();
        Recipe b = Recipe.builder().name("B").build();
        RecipeDTO recipeDTO1 = RecipeDTO.builder().recipe(a).build();
        RecipeDTO recipeDTO2 = RecipeDTO.builder().recipe(b).build();

        int compareTo = recipeDTO2.compareTo(recipeDTO1);

        assertThat(compareTo).isEqualTo(1);
    }

    @Test
    void shouldReturnFirstWhenSecondNameLetterIsFirst() {
        Recipe friedPotatoes1 = Recipe.builder().name("B").build();
        Recipe friedPotatoes2 = Recipe.builder().name("A").build();
        RecipeDTO recipeDTO1 = RecipeDTO.builder().recipe(friedPotatoes1).build();
        RecipeDTO recipeDTO2 = RecipeDTO.builder().recipe(friedPotatoes2).build();

        int compareTo = recipeDTO2.compareTo(recipeDTO1);

        assertThat(compareTo).isEqualTo(-1);
    }
}