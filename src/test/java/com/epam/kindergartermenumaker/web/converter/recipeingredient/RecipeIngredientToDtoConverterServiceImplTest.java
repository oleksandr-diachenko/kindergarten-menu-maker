package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.kindergartermenumaker.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientToDtoConverterServiceImplTest {

    @Mock
    private RecipeIngredientService recipeIngredientService;
    @Mock
    private Converter<RecipeIngredient, RecipeIngredientDTO> recipeIngredientToDtoConverter;

    @InjectMocks
    private RecipeIngredientConverterServiceImpl recipeIngredientConverterService;

    @Test
    void shouldFindAllRecipeIngredientDTOsByRecipe() {
        when(recipeIngredientService.findByRecipe(recipe())).thenReturn(List.of(recipeIngredient()));
        when(recipeIngredientToDtoConverter.convert(recipeIngredient())).thenReturn(recipeIngredientDTO());

        List<RecipeIngredientDTO> recipeIngredientDTOS = recipeIngredientConverterService.findByRecipe(recipe());

        assertThat(recipeIngredientDTOS).containsExactly(recipeIngredientDTO());
    }
}