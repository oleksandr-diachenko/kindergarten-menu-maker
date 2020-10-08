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
public class CategoryToDtoConverter implements Converter<Category, CategoryDTO> {

    private final RecipeService recipeService;
    private final Converter<Recipe, RecipeDTO> recipeToDtoConverter;

    @Override
    public CategoryDTO convert(Category category) {
        List<Recipe> recipes = recipeService.findByCategory(category);
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .map(recipeToDtoConverter::convert)
                .collect(Collectors.toList());
        return CategoryDTO.builder()
                .category(category)
                .recipes(recipeDTOS)
                .build();
    }
}
