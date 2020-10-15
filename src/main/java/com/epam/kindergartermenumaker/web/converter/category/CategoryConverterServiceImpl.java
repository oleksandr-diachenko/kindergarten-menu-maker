package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.bussiness.service.search.Searcher;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    private final Converter<Category, CategoryDTO> categoryToDtoConverter;
    private final Set<Searcher<RecipeIngredient>> searchers;

    @Override
    public List<CategoryDTO> getAllNonEmptyCategories() {
        return categoryService.findAll().stream()
                .map(categoryToDtoConverter::convert)
                .filter(categoryDTO -> isNotEmpty(categoryDTO.getRecipes()))
                .collect(toList());
    }

    @Override
    public List<CategoryDTO> getCategoriesByFilter(String filter) {
        List<CategoryDTO> categories = getAllNonEmptyCategories();
        return categories.stream()
                .filter(categoryDTO -> contains(categoryDTO, filter))
                .collect(toList());
    }

    private boolean contains(CategoryDTO categoryDTO, String filter) {
        return categoryDTO.getRecipes().stream()
                .map(RecipeDTO::getIngredients)
                .flatMap(Collection::stream)
                .map(RecipeIngredientDTO::getRecipeIngredient)
                .anyMatch(recipeIngredient -> contains(recipeIngredient, filter));
    }

    private boolean contains(RecipeIngredient recipeIngredient, String filter) {
        return searchers.stream()
                .anyMatch(searcher -> searcher.contains(recipeIngredient, filter));
    }
}
