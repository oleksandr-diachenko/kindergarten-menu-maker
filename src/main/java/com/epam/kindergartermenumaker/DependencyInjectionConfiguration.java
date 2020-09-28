package com.epam.kindergartermenumaker;

import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeIngredientService;
import com.epam.kindergartermenumaker.bussiness.service.logging.RecipeService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverter;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/

@Configuration
public class DependencyInjectionConfiguration {

    @Bean
    public RecipeConverter recipeToDtoConverter(RecipeIngredientService recipeIngredientService) {
        return new RecipeConverter(recipeIngredientService);
    }

    @Bean
    public CategoryConverter categoryToDtoConverter(RecipeService recipeService, RecipeConverter recipeConverter) {
        return new CategoryConverter(recipeService, recipeConverter);
    }
}
