package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
@Component
@RequiredArgsConstructor
public class RecipesToCategoryDtosConverter implements Converter<List<Recipe>, List<CategoryDTO>> {

    private final Converter<Recipe, RecipeDTO> recipeToDtoConverter;

    @Override
    public List<CategoryDTO> convert(List<Recipe> recipe) {
        Map<Category, List<RecipeDTO>> categoryToRecipes = getCategoryToRecipeDtos(recipe);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryToRecipes.forEach((category, recipeDTOS) -> addCategoryDto(categoryDTOS, category, recipeDTOS));
        return categoryDTOS;
    }

    private Map<Category, List<RecipeDTO>> getCategoryToRecipeDtos(List<Recipe> recipe) {
        return recipe.stream()
                .collect(groupingBy(Recipe::getCategory,
                        mapping(recipeToDtoConverter::convert, toList())));
    }

    private void addCategoryDto(List<CategoryDTO> categoryDTOS, Category category, List<RecipeDTO> recipeDTOS) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .category(category)
                .recipes(recipeDTOS).build();
        categoryDTOS.add(categoryDTO);
    }
}
