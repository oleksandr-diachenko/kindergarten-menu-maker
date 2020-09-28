package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Service
@RequiredArgsConstructor
public class CategoryConverter implements Converter<Category, CategoryDTO> {

    private final RecipeService recipeService;
    private final Converter<Recipe, RecipeDTO> recipeToDtoConverter;

    @Override
    public CategoryDTO convert(Category element) {
        List<Recipe> recipes = recipeService.findByCategory(element);
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .map(recipeToDtoConverter::convert)
                .collect(Collectors.toList());
        return CategoryDTO.builder()
                .category(element)
                .recipes(recipeDTOS)
                .build();
    }
}
