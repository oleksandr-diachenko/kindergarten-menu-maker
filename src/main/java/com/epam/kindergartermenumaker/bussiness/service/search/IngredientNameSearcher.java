package com.epam.kindergartermenumaker.bussiness.service.search;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
@Component
public class IngredientNameSearcher implements Searcher<RecipeIngredient> {

    @Override
    public boolean contains(RecipeIngredient recipeIngredient, String filter) {
        Ingredient ingredient = recipeIngredient.getIngredient();
        return containsIgnoreCase(ingredient.getName(), filter);
    }
}
