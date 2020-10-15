package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/7/2020
 **/
@Component
@RequiredArgsConstructor
public class RecipeIngredientToFormConverter implements Converter<RecipeIngredient, IngredientForm> {

    @Override
    public IngredientForm convert(RecipeIngredient recipeIngredient) {
        IngredientForm ingredientForm = new IngredientForm();
        ingredientForm.setRecipeIngredientId(recipeIngredient.getId());
        ingredientForm.setIngredientName(ingredient(recipeIngredient).getName());
        ingredientForm.setNurseryGrossAmount(nurseryQuantity(recipeIngredient).getAmountGross());
        ingredientForm.setNurseryNetAmount(nurseryQuantity(recipeIngredient).getAmountNet());
        ingredientForm.setKindergartenGrossAmount(kindergartenQuantity(recipeIngredient).getAmountGross());
        ingredientForm.setKindergartenNetAmount(kindergartenQuantity(recipeIngredient).getAmountNet());
        ingredientForm.setProtein(ingredient(recipeIngredient).getProtein());
        ingredientForm.setFat(ingredient(recipeIngredient).getFat());
        ingredientForm.setCarbohydrate(ingredient(recipeIngredient).getCarbohydrate());
        return ingredientForm;
    }

    private Quantity kindergartenQuantity(RecipeIngredient recipeIngredient) {
        return recipeIngredient.getKindergartenQuantity();
    }

    private Quantity nurseryQuantity(RecipeIngredient recipeIngredient) {
        return recipeIngredient.getNurseryQuantity();
    }

    private Ingredient ingredient(RecipeIngredient recipeIngredient) {
        return recipeIngredient.getIngredient();
    }
}
