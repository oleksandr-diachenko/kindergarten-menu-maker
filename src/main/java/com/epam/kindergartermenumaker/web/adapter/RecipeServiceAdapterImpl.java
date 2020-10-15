package com.epam.kindergartermenumaker.web.adapter;

import com.epam.kindergartermenumaker.bussiness.service.logging.*;
import com.epam.kindergartermenumaker.dao.entity.*;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

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
    private final Converter<Recipe, RecipeForm> recipeToFormConverter;

    @Override
    public void save(RecipeForm recipeForm) {
        Category category = retrieveCategory(recipeForm.getCategoryName());
        Recipe recipe = retrieveRecipe(category, recipeForm.getRecipeName(), recipeForm.getRecipeDescription());
        recipeForm.getIngredients().forEach(ingredientForm -> saveRecipeIngredient(recipe, ingredientForm));
    }

    @Override
    public void update(RecipeForm recipeForm) {
        recipeService.findById(recipeForm.getRecipeId()).ifPresent(recipe -> {
            updateRecipe(recipeForm, recipe);
            updateRecipeIngredients(recipeForm, recipe);
        });
    }

    @Override
    public RecipeForm findByRecipeName(String name) {
        return recipeService.findByName(name)
                .map(recipeToFormConverter::convert)
                .orElseGet(RecipeForm::new);
    }

    private void updateRecipe(RecipeForm recipeForm, Recipe recipe) {
        checkIfRecipeAlreadyExists(recipeForm);

        recipe.setName(recipeForm.getRecipeName());
        recipe.setDescription(recipeForm.getRecipeDescription());
        Category category = retrieveCategory(recipeForm.getCategoryName());
        recipe.setCategory(category);
        recipeService.save(recipe);
    }

    private void checkIfRecipeAlreadyExists(RecipeForm recipeForm) {
        recipeService.findByName(recipeForm.getRecipeName()).ifPresent(value -> {
            if (value.getId() != recipeForm.getRecipeId()) {
                throw new IllegalArgumentException(format("Recipe with name %s already exists. " +
                        "Choose another name", value.getName()));
            }
        });
    }

    private void updateRecipeIngredients(RecipeForm recipeForm, Recipe recipe) {
        recipeForm.getIngredients().forEach(ingredientForm -> updateRecipeIngredient(recipe, ingredientForm));
    }

    private void updateRecipeIngredient(Recipe recipe, IngredientForm ingredientForm) {
        recipeIngredientService.findById(ingredientForm.getRecipeIngredientId()).ifPresent(recipeIngredient -> {
            recipeIngredient.setRecipe(recipe);
            updateMeasurement(ingredientForm, recipeIngredient);
            updateNurseryQuantity(ingredientForm, recipeIngredient);
            updateKindergartenQuantity(ingredientForm, recipeIngredient);
            updateIngredient(ingredientForm, recipeIngredient);
            recipeIngredientService.save(recipeIngredient);
        });
    }

    private void updateMeasurement(IngredientForm ingredientForm, RecipeIngredient recipeIngredient) {
        Measurement measurement = retrieveMeasurement(ingredientForm.getMeasurementDescription());
        recipeIngredient.setMeasurement(measurement);
    }

    private void updateNurseryQuantity(IngredientForm ingredientForm, RecipeIngredient recipeIngredient) {
        Quantity quantity = retrieveQuantity(ingredientForm.getNurseryNetAmount(), ingredientForm.getNurseryGrossAmount());
        recipeIngredient.setNurseryQuantity(quantity);
    }

    private void updateKindergartenQuantity(IngredientForm ingredientForm, RecipeIngredient recipeIngredient) {
        Quantity quantity = retrieveQuantity(ingredientForm.getKindergartenNetAmount(), ingredientForm.getKindergartenGrossAmount());
        recipeIngredient.setKindergartenQuantity(quantity);
    }

    private void updateIngredient(IngredientForm ingredientForm, RecipeIngredient recipeIngredient) {
        Ingredient ingredient = retrieveIngredient(ingredientForm);
        recipeIngredient.setIngredient(ingredient);
    }

    private void saveRecipeIngredient(Recipe recipe, IngredientForm ingredientForm) {
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
