package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.kindergartermenumaker.TestData.recipeIngredient;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientToDtoConverterTest {

    @InjectMocks
    private RecipeIngredientToDtoConverter converter;

    @Test
    void shouldConvertRecipeIngredientToDTO() {
        RecipeIngredientDTO recipeIngredientDTO = converter.convert(recipeIngredient());

        assertThat(recipeIngredientDTO.getRecipeIngredient()).isEqualTo(recipeIngredient());
    }
}