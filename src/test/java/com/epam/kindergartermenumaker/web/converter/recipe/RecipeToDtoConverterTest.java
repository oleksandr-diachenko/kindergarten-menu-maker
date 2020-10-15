package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.kindergartermenumaker.TestData.recipe;
import static com.epam.kindergartermenumaker.TestData.recipeIngredientDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeToDtoConverterTest {

    @InjectMocks
    private RecipeToDtoConverter converter;
    @Mock
    private RecipeIngredientConverterService recipeIngredientConverterService;

    @Test
    void shouldSetAllRecipeIngredient() {
        when(recipeIngredientConverterService.findByRecipe(recipe())).thenReturn(List.of(recipeIngredientDTO()));

        RecipeDTO recipeDTO = converter.convert(recipe());

        assertThat(recipeDTO.getRecipe()).isEqualTo(recipe());
        assertThat(recipeDTO.getIngredients()).containsExactly(recipeIngredientDTO());
    }
}
