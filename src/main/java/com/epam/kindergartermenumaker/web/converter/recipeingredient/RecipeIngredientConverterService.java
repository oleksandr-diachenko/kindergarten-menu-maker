package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Recipe;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
public interface RecipeIngredientConverterService {

    List<RecipeIngredientDTO> findByRecipe(Recipe recipe);
}
