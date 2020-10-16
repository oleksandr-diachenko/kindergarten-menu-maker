package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;
import lombok.*;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Builder
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RecipeDTO implements Comparable<RecipeDTO> {

    private final Recipe recipe;
    private final List<RecipeIngredientDTO> ingredients;

    @Override
    public int compareTo(RecipeDTO o) {
        return recipe.getName().compareTo(o.getRecipe().getName());
    }
}
