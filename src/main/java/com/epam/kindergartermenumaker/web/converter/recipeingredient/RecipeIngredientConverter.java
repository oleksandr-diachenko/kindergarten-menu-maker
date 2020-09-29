package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Service
public class RecipeIngredientConverter implements Converter<RecipeIngredient, RecipeIngredientDTO> {

    @Override
    public RecipeIngredientDTO convert(RecipeIngredient element) {
        return RecipeIngredientDTO.builder()
                .recipeIngredient(element)
                .build();
    }
}
