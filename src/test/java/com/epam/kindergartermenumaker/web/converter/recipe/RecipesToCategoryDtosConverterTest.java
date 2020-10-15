package com.epam.kindergartermenumaker.web.converter.recipe;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
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
 * @since : 10/15/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipesToCategoryDtosConverterTest {

    @InjectMocks
    private RecipesToCategoryDtosConverter converter;

    @Mock
    private Converter<Recipe, RecipeDTO> recipeToDtoConverter;

    @Test
    void shouldReturnCategoryDtos() {
        when(recipeToDtoConverter.convert(recipe())).thenReturn(recipeDTO());

        List<CategoryDTO> categoryDTOS = converter.convert(List.of(recipe()));

        assertThat(categoryDTOS).containsExactly(categoryDTO());
    }
}