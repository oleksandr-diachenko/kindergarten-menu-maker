package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.bussiness.service.parser.Parser;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeServiceAdapter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    @Mock
    private RecipeServiceAdapter recipeServiceAdapter;
    @Mock
    private MultipartFile file;
    @Mock
    private Parser<RecipeForm> parser;
    @Mock
    private InputStream inputStream;

    @Test
    void shouldSetRecipeDTOsAndReturnRecipesPage() {
        Category mainCourse = Category.builder().name(MAIN_COURSE).build();
        Recipe friedPotatoes = Recipe.builder().name(FRIED_POTATOES).category(mainCourse).build();
        List<RecipeIngredientDTO> friedPotatoesRecipeIngredients = List.of(buildRecipeIngredientDTO(friedPotatoes));
        RecipeDTO friedPotatoesDTO = buildRecipeDTO(friedPotatoes, friedPotatoesRecipeIngredients);
        List<RecipeDTO> recipeDTOs = List.of(friedPotatoesDTO);
        CategoryDTO mainCourseDTO = buildCategoryDTO(mainCourse, recipeDTOs);
        List<CategoryDTO> categoriesDTOs = List.of(mainCourseDTO);
        when(service.getAllNonEmptyCategories()).thenReturn(categoriesDTOs);

        String page = controller.getAllCategories(model);

        assertThat(page).isEqualTo("recipes");
        verify(model).addAttribute("categories", categoriesDTOs);
    }

    @Test
    void shouldSaveRecipeFormAndRedirectToRecipesPageWhenRecipeIdIsZero() {
        RecipeForm recipeForm = new RecipeForm();

        String page = controller.updateRecipe(recipeForm);

        assertThat(page).isEqualTo("redirect:/recipes");
        verify(recipeServiceAdapter).save(recipeForm);
        verify(recipeServiceAdapter, never()).update(recipeForm);
    }

    @Test
    void shouldUpdateRecipeFormAndRedirectToRecipesPageWhenRecipeIdIsMoreTheZero() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(1);

        String page = controller.updateRecipe(recipeForm);

        assertThat(page).isEqualTo("redirect:/recipes");
        verify(recipeServiceAdapter).update(recipeForm);
        verify(recipeServiceAdapter, never()).save(recipeForm);
    }

    @Test
    void shouldSetRecipeFormToModelAndReturnUpdateRecipePage() {
        ArgumentCaptor<RecipeForm> captor = ArgumentCaptor.forClass(RecipeForm.class);

        String page = controller.getCreateRecipeForm(model, 2);

        verify(model).addAttribute(eq("recipeForm"), captor.capture());
        assertThat(captor.getValue().getIngredients()).hasSize(2);
        assertThat(page).isEqualTo("update-recipe");
    }

    @Test
    void shouldSetRecipeFormAndReturnUpdateRecipePageWhenFileUploaded() throws IOException {
        when(file.getInputStream()).thenReturn(inputStream);
        RecipeForm recipeForm = new RecipeForm();
        when(parser.parse(inputStream)).thenReturn(recipeForm);

        String page = controller.loadRecipe(model, file);

        verify(parser).parse(inputStream);
        verify(model).addAttribute("recipeForm", recipeForm);
        assertThat(page).isEqualTo("update-recipe");
    }

    @Test
    void shouldSetRecipeFormOnUpdate() {
        when(recipeServiceAdapter.findByRecipeName(FRIED_POTATOES)).thenReturn(new RecipeForm());

        String page = controller.getUpdateRecipeForm(model, FRIED_POTATOES);

        verify(model).addAttribute("recipeForm", new RecipeForm());
        assertThat(page).isEqualTo("update-recipe");
    }

    private CategoryDTO buildCategoryDTO(Category category, List<RecipeDTO> recipeDTOS) {
        return CategoryDTO.builder()
                .category(category)
                .recipes(recipeDTOS)
                .build();
    }

    private RecipeIngredientDTO buildRecipeIngredientDTO(Recipe recipe) {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .id(10)
                .recipe(recipe)
                .build();
        return RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient)
                .build();
    }

    private RecipeDTO buildRecipeDTO(Recipe recipe, List<RecipeIngredientDTO> recipeIngredients) {
        return RecipeDTO.builder()
                .recipe(recipe)
                .ingredients(recipeIngredients)
                .build();
    }
}