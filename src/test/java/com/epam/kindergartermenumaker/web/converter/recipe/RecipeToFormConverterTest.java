package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientToFormConverter;
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
 * @since : 10/8/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeToFormConverterTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String FRIED_POTATOES_IN_A_SKILLET = "Fried potatoes in a skillet";
    private static final String MAIN_SOURCE = "Main source";

    @InjectMocks
    private RecipeToFormConverter converter;

    @Mock
    private RecipeIngredientService recipeIngredientService;
    @Mock
    private RecipeIngredientToFormConverter recipeIngredientToFormConverter;

    @Test
    void shouldConvertRecipeToRecipeForm() {
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        Recipe friedPotatoes = Recipe.builder().id(1).name(FRIED_POTATOES).category(mainSource)
                .description(FRIED_POTATOES_IN_A_SKILLET).build();
        when(recipeIngredientService.findByRecipe(friedPotatoes)).thenReturn(List.of(new RecipeIngredient()));
        when(recipeIngredientToFormConverter.convert(new RecipeIngredient())).thenReturn(new IngredientForm());

        RecipeForm recipeForm = converter.convert(friedPotatoes);

        assertThat(recipeForm.getRecipeId()).isEqualTo(1);
        assertThat(recipeForm.getCategoryName()).isEqualTo(MAIN_SOURCE);
        assertThat(recipeForm.getRecipeName()).isEqualTo(FRIED_POTATOES);
        assertThat(recipeForm.getRecipeDescription()).isEqualTo(FRIED_POTATOES_IN_A_SKILLET);
        assertThat(recipeForm.getIngredients()).containsExactly(new IngredientForm());
    }
}