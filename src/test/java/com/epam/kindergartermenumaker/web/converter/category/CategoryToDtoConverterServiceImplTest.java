package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.bussiness.service.search.Searcher;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/28/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryToDtoConverterServiceImplTest {

    private static final String MAIN_SOURCE = "Main source";

    @InjectMocks
    private CategoryConverterServiceImpl categoryConverterService;

    @Mock
    private CategoryService categoryService;
    @Mock
    private Converter<Category, CategoryDTO> categoryToCategoryDTOConverter;
    @Mock
    private Searcher<RecipeIngredient> searcher;
    @Spy
    private final HashSet<Searcher<RecipeIngredient>> searchers = new HashSet<>();

    @BeforeEach
    void setUp() {
        searchers.add(searcher);
    }

    @Test
    void shouldReturnListOfCategoryDTOs() {
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        when(categoryService.findAll()).thenReturn(List.of(mainSource));
        RecipeDTO recipe = RecipeDTO.builder().build();
        CategoryDTO categoryDTO = CategoryDTO.builder().category(mainSource).recipes(List.of(recipe)).build();
        when(categoryToCategoryDTOConverter.convert(mainSource)).thenReturn(categoryDTO);

        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllNonEmptyCategories();

        assertThat(categoryDTOS).containsExactly(categoryDTO);
    }

    @Test
    void shouldNotReturnCategoryWhenRecipesIsEmpty() {
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        when(categoryService.findAll()).thenReturn(List.of(mainSource));
        CategoryDTO categoryDTO = CategoryDTO.builder().category(mainSource).build();
        when(categoryToCategoryDTOConverter.convert(mainSource)).thenReturn(categoryDTO);

        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllNonEmptyCategories();

        assertThat(categoryDTOS).isEmpty();
    }

    @Test
    void shouldReturnCategoryDtoWhenSearcherFindOne() {
        Category category = Category.builder().build();
        Recipe recipe = Recipe.builder().build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().build();
        RecipeIngredientDTO recipeIngredientDTO = RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient).build();
        RecipeDTO recipeDTO = RecipeDTO.builder().recipe(recipe).ingredients(List.of(recipeIngredientDTO)).build();
        CategoryDTO categoryDTO = CategoryDTO.builder().category(category).recipes(List.of(recipeDTO)).build();
        when(categoryService.findAll()).thenReturn(List.of(category));
        when(categoryToCategoryDTOConverter.convert(category)).thenReturn(categoryDTO);
        when(searcher.contains(recipeIngredient, "qwe")).thenReturn(true);

        List<CategoryDTO> categoriesByFilter = categoryConverterService.getCategoriesByFilter("qwe");

        assertThat(categoriesByFilter).containsExactly(categoryDTO);
    }

    @Test
    void shouldReturnEmptyListWhenSearcherNotFindOne() {
        Category category = Category.builder().build();
        Recipe recipe = Recipe.builder().build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().build();
        RecipeIngredientDTO recipeIngredientDTO = RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient).build();
        RecipeDTO recipeDTO = RecipeDTO.builder().recipe(recipe).ingredients(List.of(recipeIngredientDTO)).build();
        CategoryDTO categoryDTO = CategoryDTO.builder().category(category).recipes(List.of(recipeDTO)).build();
        when(categoryService.findAll()).thenReturn(List.of(category));
        when(categoryToCategoryDTOConverter.convert(category)).thenReturn(categoryDTO);
        when(searcher.contains(any(), anyString())).thenReturn(false);

        List<CategoryDTO> categoriesByFilter = categoryConverterService.getCategoriesByFilter("qwe");

        assertThat(categoriesByFilter).isEmpty();
    }
}