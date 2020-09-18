package com.epam.kindergartermenumaker.bussiness.service.converter;

import com.epam.kindergartermenumaker.RecipeFactory;
import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.kindergartermenumaker.RecipeType.FRIED_POTATOES;
import static com.epam.kindergartermenumaker.RecipeType.MILK_COCKTAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/21/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeConverterServiceImplTest {

    @InjectMocks
    private RecipeConverterServiceImpl recipeConverterService;

    @Mock
    private RecipeService recipeService;
    @Mock
    private Converter<Recipe, RecipeDTO> converter;

    private final List<RecipeIngredient> friedPotatoes = RecipeFactory.create(FRIED_POTATOES);
    private final List<RecipeIngredient> milkCocktail = RecipeFactory.create(MILK_COCKTAIL);

    @Test
    void shouldReturnAllConverterRecipes() {
        Recipe friedPotatoesRecipe = friedPotatoes.get(0).getRecipe();
        RecipeDTO friedPotatoesRecipeDTO = createRecipeDTO(friedPotatoesRecipe, friedPotatoes);
        when(converter.convert(friedPotatoesRecipe)).thenReturn(friedPotatoesRecipeDTO);

        Recipe milkCocktailRecipe = milkCocktail.get(0).getRecipe();
        RecipeDTO milkCocktailRecipeDTO = createRecipeDTO(milkCocktailRecipe, milkCocktail);
        when(converter.convert(milkCocktailRecipe)).thenReturn(milkCocktailRecipeDTO);

        when(recipeService.findAll()).thenReturn(List.of(friedPotatoesRecipe, milkCocktailRecipe));

        List<RecipeDTO> actual = recipeConverterService.getAllRecipes();

        assertThat(actual).containsExactly(friedPotatoesRecipeDTO, milkCocktailRecipeDTO);
    }

    private RecipeDTO createRecipeDTO(Recipe recipe, List<RecipeIngredient> recipeIngredients) {
        return RecipeDTO.builder().recipe(recipe).ingredients(recipeIngredients).build();
    }
}