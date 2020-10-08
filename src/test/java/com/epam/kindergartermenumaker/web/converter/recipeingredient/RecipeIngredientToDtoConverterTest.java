package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientToDtoConverterTest {

    private static final String FRIED_POTATOES = "Fried potatoes";

    @InjectMocks
    private RecipeIngredientToDtoConverter converter;

    @Test
    void shouldConvertRecipeIngredientToDTO() {
        Recipe recipe = Recipe.builder().name(FRIED_POTATOES).build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();

        RecipeIngredientDTO recipeIngredientDTO = converter.convert(recipeIngredient);

        assertThat(recipeIngredientDTO.getRecipeIngredient()).isEqualTo(recipeIngredient);
    }
}