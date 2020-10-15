package com.epam.kindergartermenumaker;

import com.epam.kindergartermenumaker.dao.entity.*;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import com.epam.kindergartermenumaker.web.converter.recipeingredient.RecipeIngredientDTO;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/21/2020
 **/
public class TestData {

    public static final String POTATO = "Potato";
    public static final String GRAM = "Gram";
    public static final String FRIED_POTATOES = "Fried potatoes";
    public static final String FRIED_POTATOES_IN_A_SKILLET = "Fried potatoes in a skillet";
    public static final String MAIN_COURSE = "Main course";

    private TestData() {
        throw new UnsupportedOperationException();
    }

    public static Category category() {
        return Category.builder().id(1).name(MAIN_COURSE).build();
    }

    public static Recipe recipe() {
        return Recipe.builder()
                .id(1)
                .category(category())
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_IN_A_SKILLET).build();
    }

    public static Ingredient ingredient() {
        return Ingredient.builder().id(1).name(POTATO).protein(2).fat(3).carbohydrate(4).build();
    }

    public static Measurement measurement() {
        return Measurement.builder().id(1).description(GRAM).build();
    }

    public static Quantity nursery() {
        return Quantity.builder().id(1).amountNet(5).amountGross(6).build();
    }

    public static Quantity kindergarten() {
        return Quantity.builder().id(2).amountNet(7).amountGross(8).build();
    }

    public static RecipeIngredient recipeIngredient() {
        return RecipeIngredient.builder()
                .id(1)
                .recipe(recipe())
                .measurement(measurement())
                .ingredient(ingredient())
                .nurseryQuantity(nursery())
                .kindergartenQuantity(kindergarten()).build();
    }

    public static CategoryDTO categoryDTO() {
        return CategoryDTO.builder().category(category()).recipes(List.of(recipeDTO())).build();
    }

    public static RecipeDTO recipeDTO() {
        return RecipeDTO.builder().recipe(recipe()).ingredients(List.of(recipeIngredientDTO())).build();
    }

    public static RecipeIngredientDTO recipeIngredientDTO() {
        return RecipeIngredientDTO.builder().recipeIngredient(recipeIngredient()).build();
    }

    public static IngredientForm ingredientForm() {
        IngredientForm ingredientForm = new IngredientForm();
        ingredientForm.setIngredientName(ingredient().getName());
        ingredientForm.setNurseryNetAmount(nursery().getAmountNet());
        ingredientForm.setKindergartenNetAmount(kindergarten().getAmountNet());
        ingredientForm.setNurseryGrossAmount(nursery().getAmountGross());
        ingredientForm.setKindergartenGrossAmount(kindergarten().getAmountGross());
        ingredientForm.setProtein(ingredient().getProtein());
        ingredientForm.setFat(ingredient().getFat());
        ingredientForm.setCarbohydrate(ingredient().getCarbohydrate());
        ingredientForm.setMeasurementDescription(measurement().getDescription());
        return ingredientForm;
    }

    public static RecipeForm recipeForm() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setCategoryName(category().getName());
        recipeForm.setRecipeId(recipe().getId());
        recipeForm.setRecipeName(recipe().getName());
        recipeForm.setRecipeDescription(recipe().getDescription());
        recipeForm.setIngredients(List.of(ingredientForm()));
        return recipeForm;
    }
}
