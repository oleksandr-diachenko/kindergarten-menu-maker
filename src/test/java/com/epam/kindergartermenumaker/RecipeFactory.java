package com.epam.kindergartermenumaker;

import com.epam.kindergartermenumaker.dao.entity.*;

import java.util.Collections;
import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/21/2020
 **/
public class RecipeFactory {

    private static final String POTATO = "Potato";
    private static final String SALT = "Salt";
    private static final String PEPPER = "Pepper";
    private static final String OIL = "Oil";
    private static final String GRAM = "Gram";
    private static final String MILLILITER = "Milliliter";
    private static final int ONE = 1;
    private static final int TEN = 10;
    private static final int FIVE = 5;
    private static final int FIFTY = 50;
    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String FRIED_POTATOES_IN_A_SKILLET = "Fried potatoes in a skillet";
    private static final String MILK_COCKTAIL = "Milk cocktail";
    private static final String MILK_COCKTAIL_WITH_JEM = "Milk cocktail with jem";
    private static final String MILK = "Milk";
    private static final String JEM = "Jem";
    private static final String SUGAR = "Sugar";
    private static final int HUNDRED = 100;

    private RecipeFactory() {
        throw new UnsupportedOperationException();
    }

    public static List<RecipeIngredient> create(RecipeType type) {
        if (type == RecipeType.FRIED_POTATOES) {
            return createFriedPotatoes();
        } else if(type == RecipeType.MILK_COCKTAIL) {
            return createMilkCocktail();
        }
        return Collections.emptyList();
    }

    private static List<RecipeIngredient> createMilkCocktail() {
        Recipe milkCocktail = prepareRecipe(MILK_COCKTAIL, MILK_COCKTAIL_WITH_JEM);

        Ingredient milk = prepareIngredient(MILK);
        Ingredient jem = prepareIngredient(JEM);
        Ingredient sugar = prepareIngredient(SUGAR);

        Measurement gram = prepareMeasurement(GRAM);
        Measurement ml = prepareMeasurement(MILLILITER);

        Quantity hundred = prepareQuantity(HUNDRED);
        Quantity fifty = prepareQuantity(FIFTY);
        Quantity ten = prepareQuantity(TEN);

        return List.of(
                prepareRecipeIngredient(milkCocktail, milk, hundred, ml),
                prepareRecipeIngredient(milkCocktail, jem, fifty, gram),
                prepareRecipeIngredient(milkCocktail, sugar, ten, gram)
        );
    }


    private static List<RecipeIngredient> createFriedPotatoes() {
        Recipe friedPotatoes = prepareRecipe(FRIED_POTATOES, FRIED_POTATOES_IN_A_SKILLET);

        Ingredient potato = prepareIngredient(POTATO);
        Ingredient salt = prepareIngredient(SALT);
        Ingredient pepper = prepareIngredient(PEPPER);
        Ingredient oil = prepareIngredient(OIL);

        Measurement gram = prepareMeasurement(GRAM);
        Measurement ml = prepareMeasurement(MILLILITER);

        Quantity one = prepareQuantity(ONE);
        Quantity ten = prepareQuantity(TEN);
        Quantity five = prepareQuantity(FIVE);
        Quantity fifty = prepareQuantity(FIFTY);

        return List.of(
                prepareRecipeIngredient(friedPotatoes, potato, one, gram),
                prepareRecipeIngredient(friedPotatoes, salt, ten, gram),
                prepareRecipeIngredient(friedPotatoes, pepper, five, gram),
                prepareRecipeIngredient(friedPotatoes, oil, fifty, ml)
        );
    }

    private static RecipeIngredient prepareRecipeIngredient(Recipe recipe, Ingredient ingredient, Quantity quantity, Measurement measurement) {
        return RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .kindergartenQuantity(quantity)
                .measurement(measurement)
                .build();

    }

    private static Measurement prepareMeasurement(String description) {
        return Measurement.builder().description(description).build();
    }

    private static Quantity prepareQuantity(int amount) {
        return Quantity.builder().amountNet(amount).build();
    }

    private static Ingredient prepareIngredient(String name) {
        return Ingredient.builder().name(name).build();
    }

    private static Recipe prepareRecipe(String name, String description) {
        return Recipe.builder()
                .name(name)
                .description(description)
                .build();
    }
}
