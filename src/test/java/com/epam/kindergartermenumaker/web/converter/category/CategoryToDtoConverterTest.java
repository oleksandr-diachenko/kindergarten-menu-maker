package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
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
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryToDtoConverterTest {

    @InjectMocks
    private CategoryToDtoConverter converter;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Converter<Recipe, RecipeDTO> recipeToDTOConverter;

    @Test
    void shouldSetAllRecipeIngredient() {
        when(recipeService.findByCategory(category())).thenReturn(List.of(recipe()));
        when(recipeToDTOConverter.convert(recipe())).thenReturn(recipeDTO());

        CategoryDTO recipeDTO = converter.convert(category());

        assertThat(recipeDTO.getCategory()).isEqualTo(category());
        assertThat(recipeDTO.getRecipes()).containsExactly(recipeDTO());
    }
}
