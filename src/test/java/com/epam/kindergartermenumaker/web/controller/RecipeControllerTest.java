package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.bussiness.service.converter.RecipeConverterService;
import com.epam.kindergartermenumaker.bussiness.service.converter.RecipeDTO;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private static final String FRIED_POTATOES = "Fried potatoes";

    @InjectMocks
    private RecipeController controller;
    @Mock
    private RecipeConverterService service;
    @Mock
    private Model model;

    @Test
    void shouldSetRecipeDTOsAndReturnRecipesPage() {
        Recipe friedPotatoes = Recipe.builder().name(FRIED_POTATOES).build();
        List<RecipeIngredient> friedPotatoesRecipeIngredients = List.of(buildRecipeIngredient(friedPotatoes));
        RecipeDTO friedPotatoesDTO = buildRecipeDTO(friedPotatoes, friedPotatoesRecipeIngredients);
        List<RecipeDTO> recipeDTOs = List.of(friedPotatoesDTO);
        when(service.getAllRecipes()).thenReturn(recipeDTOs);

        String allRecipes = controller.getAllRecipes(model);

        assertThat(allRecipes).isEqualTo("recipes");
        verify(model).addAttribute("recipes", recipeDTOs);
    }

    private RecipeIngredient buildRecipeIngredient(Recipe recipe) {
        return RecipeIngredient.builder()
                .id(10)
                .recipe(recipe)
                .build();
    }

    private RecipeDTO buildRecipeDTO(Recipe recipe, List<RecipeIngredient> recipeIngredients) {
        return RecipeDTO.builder()
                .recipe(recipe)
                .ingredients(recipeIngredients)
                .build();
    }
}