package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.*;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientConverterService;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeConverterTest {

    private static final String POTATO = "Potato";
    private static final String GRAM = "Gram";
    private static final int ONE = 1;
    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String FRIED_POTATOES_IN_A_SKILLET = "Fried potatoes in a skillet";

    @InjectMocks
    private RecipeConverter converter;
    @Mock
    private RecipeIngredientConverterService recipeIngredientConverterService;

    @Test
    void shouldSetAllRecipeIngredient() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_IN_A_SKILLET)
                .build();
        Ingredient potato = Ingredient.builder().name(POTATO).build();
        Measurement gram = Measurement.builder().description(GRAM).build();
        Quantity one = Quantity.builder().amountNet(ONE).build();

        RecipeIngredient recIngPotato = RecipeIngredient.builder()
                .recipe(friedPotatoes)
                .ingredient(potato)
                .kindergartenQuantity(one)
                .measurement(gram)
                .build();
        RecipeIngredientDTO recIngPotatoDTO = RecipeIngredientDTO.builder().recipeIngredient(recIngPotato).build();
        when(recipeIngredientConverterService.findByRecipe(friedPotatoes)).thenReturn(List.of(recIngPotatoDTO));

        RecipeDTO recipeDTO = converter.convert(friedPotatoes);

        assertThat(recipeDTO.getRecipe()).isEqualTo(friedPotatoes);
        assertThat(recipeDTO.getIngredients()).containsExactly(recIngPotatoDTO);
    }
}
