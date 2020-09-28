package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String MAIN_COURSE = "Main course";

    @InjectMocks
    private RecipeController controller;
    @Mock
    private CategoryConverterService service;
    @Mock
    private Model model;

    @Test
    void shouldSetRecipeDTOsAndReturnRecipesPage() {
        Category mainCourse = Category.builder().name(MAIN_COURSE).build();
        Recipe friedPotatoes = Recipe.builder().name(FRIED_POTATOES).category(mainCourse).build();
        List<RecipeIngredient> friedPotatoesRecipeIngredients = List.of(buildRecipeIngredient(friedPotatoes));
        RecipeDTO friedPotatoesDTO = buildRecipeDTO(friedPotatoes, friedPotatoesRecipeIngredients);
        List<RecipeDTO> recipeDTOs = List.of(friedPotatoesDTO);
        CategoryDTO mainCourseDTO = buildCategoryDTO(mainCourse, recipeDTOs);
        List<CategoryDTO> categoriesDTOs = List.of(mainCourseDTO);
        when(service.getAllCategories()).thenReturn(categoriesDTOs);

        String allCategories = controller.getAllCategories(model);

        assertThat(allCategories).isEqualTo("recipes");
        verify(model).addAttribute("categories", categoriesDTOs);
    }

    private CategoryDTO buildCategoryDTO(Category category, List<RecipeDTO> recipeDTOS) {
        return CategoryDTO.builder()
                .category(category)
                .recipes(recipeDTOS)
                .build();
    }

    private RecipeIngredient buildRecipeIngredient(Recipe recipe) {
        return RecipeIngredient.builder()
                .id(10)
                .recipe(recipe)
                .build();
    }

    private RecipeDTO buildRecipeDTO(Recipe recipe, List<RecipeIngredient> recipeIngredients) {
        return RecipeDTO.builder()
                .recipe(recipe)
                .ingredients(recipeIngredients)
                .build();
    }
}