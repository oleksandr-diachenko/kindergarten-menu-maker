package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.bussiness.service.parser.Parser;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeServiceAdapter;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.web.dialect.NumberDialect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.epam.kindergartermenumaker.TestData.*;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/16/2020
 **/
@WebMvcTest(RecipeController.class)
class RecipeControllerIntegrationTest {

    @TestConfiguration
    static class AdditionalConfig {

        @Bean
        public NumberDialect numberDialect() {
            return new NumberDialect();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryConverterService categoryConverterService;
    @MockBean
    private RecipeServiceAdapter recipeServiceAdapter;
    @MockBean
    private Parser<RecipeForm> parser;
    @MockBean
    private RecipeService recipeService;
    @MockBean
    private Converter<Recipe, RecipeDTO> recipeToDtoConverter;

    @Test
    void shouldStatusOkAndCallAllCategoriesOnGetRecipes() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());

        verify(categoryConverterService).getAllNonEmptyCategories();
    }

    @Test
    void shouldStatusOkAndSetRecipeFormWithOneIngredientOnGetCreate() throws Exception {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setIngredients(List.of(new IngredientForm()));

        mockMvc.perform(get("/recipes/create"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeForm", recipeForm));
    }

    @Test
    void shouldStatusOkAndCallAdapterForNameOnGetUpdate() throws Exception {
        when(recipeServiceAdapter.findByRecipeName(recipe().getName())).thenReturn(recipeForm());

        mockMvc.perform(get("/recipes/update")
                .param("name", recipe().getName()))
                .andExpect(status().isOk());

        verify(recipeServiceAdapter).findByRecipeName(recipe().getName());
    }

    @Test
    void shouldStatusRedirectAndCallAdapterOnPostRecipes() throws Exception {
        mockMvc.perform(post("/recipes")
                .flashAttr("recipeForm", recipeForm()))
                .andExpect(status().is3xxRedirection());

        verify(recipeServiceAdapter).update(recipeForm());
    }

    @Test
    void shouldStatusOkOnPostLoad() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes()
        );
        when(parser.parse(any())).thenReturn(recipeForm());

        mockMvc.perform(multipart("/recipes/load")
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void shouldStatusOkOnPostCalculate() throws Exception {
        when(recipeService.findByName(recipe().getName())).thenReturn(of(recipe()));
        when(recipeToDtoConverter.convert(recipe())).thenReturn(recipeDTO());

        mockMvc.perform(post("/recipes/calculate")
                .param("name", recipe().getName()))
                .andExpect(status().isOk());
    }
}