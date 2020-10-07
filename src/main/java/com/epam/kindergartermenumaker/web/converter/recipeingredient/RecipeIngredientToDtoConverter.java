package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Service
public class RecipeIngredientToDtoConverter implements Converter<RecipeIngredient, RecipeIngredientDTO> {

    @Override
    public RecipeIngredientDTO convert(RecipeIngredient recipeIngredient) {
        return RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient)
                .build();
    }
}
