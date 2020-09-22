package com.epam.kindergartermenumaker.bussiness.service.converter;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Service
@RequiredArgsConstructor
public class RecipeConverter implements Converter<Recipe, RecipeDTO> {

    private final RecipeIngredientService recipeIngredientService;

    @Override
    public RecipeDTO convert(Recipe recipe) {
        return RecipeDTO.builder()
                .recipe(recipe)
                .ingredients(recipeIngredientService.findByRecipe(recipe))
                .build();
    }
}
