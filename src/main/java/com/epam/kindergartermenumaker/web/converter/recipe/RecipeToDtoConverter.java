package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Component
@RequiredArgsConstructor
public class RecipeToDtoConverter implements Converter<Recipe, RecipeDTO> {

    private final RecipeIngredientConverterService recipeIngredientConverterService;

    @Override
    public RecipeDTO convert(Recipe recipe) {
        return RecipeDTO.builder()
                .recipe(recipe)
                .ingredients(recipeIngredientConverterService.findByRecipe(recipe))
                .build();
    }
}
