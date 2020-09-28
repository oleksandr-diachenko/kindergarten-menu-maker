package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.web.converter.Converter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryConverterTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String MAIN_SOURCE = "Main source";

    @InjectMocks
    private CategoryConverter converter;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Converter<Recipe, RecipeDTO> recipeToDTOConverter;

    @Test
    void shouldSetAllRecipeIngredient() {
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        Recipe friedPotatoes = Recipe.builder().name(FRIED_POTATOES).build();
        when(recipeService.findByCategory(mainSource)).thenReturn(List.of(friedPotatoes));
        RecipeDTO friedPotatoesDTO = RecipeDTO.builder().recipe(friedPotatoes).build();
        when(recipeToDTOConverter.convert(friedPotatoes)).thenReturn(friedPotatoesDTO);

        CategoryDTO recipeDTO = converter.convert(mainSource);

        assertThat(recipeDTO.getCategory()).isEqualTo(mainSource);
        assertThat(recipeDTO.getRecipes()).containsExactly(friedPotatoesDTO);
    }
}
