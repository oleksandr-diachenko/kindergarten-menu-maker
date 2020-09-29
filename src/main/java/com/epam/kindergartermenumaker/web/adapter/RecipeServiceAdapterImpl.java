package com.epam.kindergartermenumaker.web.adapter;

import com.epam.kindergartermenumaker.bussiness.service.logging.*;
import com.epam.kindergartermenumaker.dao.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Service
@RequiredArgsConstructor
public class RecipeServiceAdapterImpl implements RecipeServiceAdapter {

    private final CategoryService categoryService;
    private final RecipeService recipeService;
    private final QuantityService quantityService;
    private final IngredientService ingredientService;
    private final MeasurementService measurementService;
    private final RecipeIngredientService recipeIngredientService;

    @Override
    public void save(RecipeForm recipeForm) {
        Category category = retrieveCategory(recipeForm.getCategoryName());
        Recipe recipe = retrieveRecipe(category, recipeForm.getRecipeName(), recipeForm.getRecipeDescription());
        for (IngredientForm ingredientForm : recipeForm.getIngredients()) {
            Quantity nurseryQuantity = retrieveQuantity(ingredientForm.getNurseryNetAmount(), ingredientForm.getNurseryGrossAmount());
            Quantity kindergartenQuantity = retrieveQuantity(ingredientForm.getKindergartenNetAmount(), ingredientForm.getKindergartenGrossAmount());
            Ingredient ingredient = retrieveIngredient(ingredientForm);
            Measurement measurement = retrieveMeasurement(ingredientForm.getMeasurementDescription());
            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                    .recipe(recipe)
                    .nurseryQuantity(nurseryQuantity)
                    .kindergartenQuantity(kindergartenQuantity)
                    .ingredient(ingredient)
                    .measurement(measurement)
                    .build();
            recipeIngredientService.save(recipeIngredient);
        }
    }

    private Measurement retrieveMeasurement(String measurementDescription) {
        return measurementService.findByDescription(measurementDescription)
                .orElseGet(() -> {
                    Measurement measurement = Measurement.builder().description(measurementDescription).build();
                    return measurementService.save(measurement);
                });
    }

    private Ingredient retrieveIngredient(IngredientForm ingredientForm) {
        return ingredientService.findByName(ingredientForm.getIngredientName())
                .orElseGet(() -> {
                    Ingredient ingredient = Ingredient.builder()
                            .name(ingredientForm.getIngredientName())
                            .fat(ingredientForm.getFat())
                            .protein(ingredientForm.getProtein())
                            .carbohydrate(ingredientForm.getCarbohydrate()).build();
                    return ingredientService.save(ingredient);
                });
    }

    private Quantity retrieveQuantity(double amountNet, double amountGross) {
        return quantityService.findByAmountNetAndAmountGross(amountNet, amountGross)
                .orElseGet(() -> {
                    Quantity quantity = Quantity.builder().amountNet(amountNet).amountGross(amountGross).build();
                    return quantityService.save(quantity);
                });
    }

    private Recipe retrieveRecipe(Category category, String recipeName, String recipeDescription) {
        return recipeService.findByName(recipeName)
                .orElseGet(() -> {
                    Recipe recipe = Recipe.builder()
                            .category(category)
                            .name(recipeName)
                            .description(recipeDescription).build();
                    return recipeService.save(recipe);
                });
    }

    private Category retrieveCategory(String categoryName) {
        return categoryService.findByName(categoryName)
                .orElseGet(() -> {
                    Category category = Category.builder().name(categoryName).build();
                    return categoryService.save(category);
                });
    }
}
