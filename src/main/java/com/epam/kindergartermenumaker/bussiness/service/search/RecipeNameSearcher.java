package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
@Component
public class RecipeNameSearcher implements Searcher<RecipeIngredient> {

    @Override
    public boolean contains(RecipeIngredient recipeIngredient, String filter) {
        Recipe recipe = recipeIngredient.getRecipe();
        return containsIgnoreCase(recipe.getName(), filter);
    }
}
