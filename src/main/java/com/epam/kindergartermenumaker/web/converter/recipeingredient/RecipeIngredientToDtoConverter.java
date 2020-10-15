package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Component
public class RecipeIngredientToDtoConverter implements Converter<RecipeIngredient, RecipeIngredientDTO> {

    @Override
    public RecipeIngredientDTO convert(RecipeIngredient recipeIngredient) {
        return RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient)
                .build();
    }
}
