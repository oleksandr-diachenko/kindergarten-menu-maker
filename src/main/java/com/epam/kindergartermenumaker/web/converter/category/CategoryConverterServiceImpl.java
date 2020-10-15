package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Service
@RequiredArgsConstructor
public class CategoryConverterServiceImpl implements CategoryConverterService {

    private final CategoryService categoryService;
    private final RecipeService recipeService;
    private final Converter<Category, CategoryDTO> categoryToDtoConverter;
    private final Converter<List<Recipe>, List<CategoryDTO>> recipesToCategoryDtosConverter;

    @Override
    public List<CategoryDTO> getAllNonEmptyCategories() {
        return categoryService.findAll().stream()
                .map(categoryToDtoConverter::convert)
                .filter(categoryDTO -> isNotEmpty(categoryDTO.getRecipes()))
                .collect(toList());
    }

    @Override
    public List<CategoryDTO> getCategoriesByFilter(String filter) {
        List<Recipe> recipes = recipeService.findByNameContainsIgnoreCase(filter);
        return recipesToCategoryDtosConverter.convert(recipes);
    }
}
