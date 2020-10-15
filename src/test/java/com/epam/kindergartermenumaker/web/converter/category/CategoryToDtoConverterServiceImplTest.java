package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.bussiness.service.search.Searcher;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static com.epam.kindergartermenumaker.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/28/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryToDtoConverterServiceImplTest {

    @InjectMocks
    private CategoryConverterServiceImpl categoryConverterService;

    @Mock
    private CategoryService categoryService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Converter<Category, CategoryDTO> categoryToCategoryDTOConverter;
    @Mock
    private Converter<List<Recipe>, List<CategoryDTO>> recipesToCategoryDtosConverter;
    @Mock
    private Searcher<RecipeIngredient> searcher;
    @Spy
    private final HashSet<Searcher<RecipeIngredient>> searchers = new HashSet<>();

    @BeforeEach
    void setUp() {
        categoryConverterService = new CategoryConverterServiceImpl(categoryService, recipeService,
                categoryToCategoryDTOConverter, recipesToCategoryDtosConverter);
        searchers.add(searcher);
    }

    @Test
    void shouldReturnListOfCategoryDTOs() {
        when(categoryService.findAll()).thenReturn(List.of(category()));
        when(categoryToCategoryDTOConverter.convert(category())).thenReturn(categoryDTO());

        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllNonEmptyCategories();

        assertThat(categoryDTOS).containsExactly(categoryDTO());
    }

    @Test
    void shouldNotReturnCategoryWhenRecipesIsEmpty() {
        when(categoryService.findAll()).thenReturn(List.of(category()));
        CategoryDTO categoryDTO = categoryDTO();
        categoryDTO.setRecipes(List.of());
        when(categoryToCategoryDTOConverter.convert(category())).thenReturn(categoryDTO);

        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllNonEmptyCategories();

        assertThat(categoryDTOS).isEmpty();
    }

    @Test
    void shouldReturnCategoryDtoWhenSearcherFindOne() {
        when(recipeService.findByNameContainsIgnoreCase(FRIED_POTATOES)).thenReturn(List.of(recipe()));
        when(recipesToCategoryDtosConverter.convert(List.of(recipe()))).thenReturn(List.of(categoryDTO()));

        List<CategoryDTO> categoriesByFilter = categoryConverterService.getCategoriesByFilter(FRIED_POTATOES);

        assertThat(categoriesByFilter).containsExactly(categoryDTO());
    }

    @Test
    void shouldReturnEmptyListWhenSearcherNotFindOne() {
        when(recipeService.findByNameContainsIgnoreCase(FRIED_POTATOES)).thenReturn(List.of());
        when(recipesToCategoryDtosConverter.convert(any())).thenReturn(List.of());

        List<CategoryDTO> categoriesByFilter = categoryConverterService.getCategoriesByFilter(FRIED_POTATOES);

        assertThat(categoriesByFilter).isEmpty();
    }
}