package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Service
@RequiredArgsConstructor
public class RecipeIngredientConverterServiceImpl implements RecipeIngredientConverterService {

    private final RecipeIngredientService recipeIngredientService;
    private final Converter<RecipeIngredient, RecipeIngredientDTO> recipeIngredientToDtoConverter;

    @Override
    public List<RecipeIngredientDTO> findByRecipe(Recipe recipe) {
        return recipeIngredientService.findByRecipe(recipe).stream()
                .map(recipeIngredientToDtoConverter::convert)
                .collect(toList());
    }
}
