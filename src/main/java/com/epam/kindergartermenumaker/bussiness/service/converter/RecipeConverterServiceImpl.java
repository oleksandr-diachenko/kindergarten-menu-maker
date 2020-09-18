package com.epam.kindergartermenumaker.bussiness.service.converter;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Service
@RequiredArgsConstructor
public class RecipeConverterServiceImpl implements RecipeConverterService {

    private final RecipeService recipeService;
    private final Converter<Recipe, RecipeDTO> converter;

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.findAll().stream()
                .map(converter::convert)
                .collect(toList());
    }
}
