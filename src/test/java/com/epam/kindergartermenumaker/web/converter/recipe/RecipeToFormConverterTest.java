package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientToFormConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.kindergartermenumaker.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/8/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeToFormConverterTest {

    @InjectMocks
    private RecipeToFormConverter converter;

    @Mock
    private RecipeIngredientService recipeIngredientService;
    @Mock
    private RecipeIngredientToFormConverter recipeIngredientToFormConverter;

    @Test
    void shouldConvertRecipeToRecipeForm() {
        when(recipeIngredientService.findByRecipe(recipe())).thenReturn(List.of(recipeIngredient()));
        when(recipeIngredientToFormConverter.convert(recipeIngredient())).thenReturn(ingredientForm());

        RecipeForm recipeForm = converter.convert(recipe());

        assertThat(recipeForm.getRecipeId()).isEqualTo(1);
        assertThat(recipeForm.getCategoryName()).isEqualTo(MAIN_COURSE);
        assertThat(recipeForm.getRecipeName()).isEqualTo(FRIED_POTATOES);
        assertThat(recipeForm.getRecipeDescription()).isEqualTo(FRIED_POTATOES_IN_A_SKILLET);
        assertThat(recipeForm.getIngredients()).containsExactly(ingredientForm());
    }
}