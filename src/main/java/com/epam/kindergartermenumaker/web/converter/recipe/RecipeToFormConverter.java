package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientToFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/7/2020
 **/
@Service
@RequiredArgsConstructor
public class RecipeToFormConverter implements Converter<Recipe, RecipeForm> {

    private final RecipeIngredientToFormConverter recipeIngredientToFormConverter;
    private final RecipeIngredientService recipeIngredientService;

    @Override
    public RecipeForm convert(Recipe recipe) {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(recipe.getId());
        recipeForm.setCategoryName(recipe.getCategory().getName());
        recipeForm.setRecipeName(recipe.getName());
        recipeForm.setIngredients(getIngredientForms(recipe));
        recipeForm.setRecipeDescription(recipe.getDescription());
        return recipeForm;
    }

    private List<IngredientForm> getIngredientForms(Recipe recipe) {
        return recipeIngredientService.findByRecipe(recipe).stream()
                .map(recipeIngredientToFormConverter::convert)
                .collect(Collectors.toList());
    }
}
