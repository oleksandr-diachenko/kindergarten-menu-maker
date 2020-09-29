package com.epam.kindergartermenumaker;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeConverter;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientConverter;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientConverterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/

@Configuration
public class DependencyInjectionConfiguration {

    @Bean
    public RecipeConverter recipeToDtoConverter(RecipeIngredientConverterService recipeIngredientConverterService) {
        return new RecipeConverter(recipeIngredientConverterService);
    }

    @Bean
    public CategoryConverter categoryToDtoConverter(RecipeService recipeService, RecipeConverter recipeConverter) {
        return new CategoryConverter(recipeService, recipeConverter);
    }

    @Bean
    public RecipeIngredientConverter recipeIngredientToDtoConverter() {
        return new RecipeIngredientConverter();
    }
}
