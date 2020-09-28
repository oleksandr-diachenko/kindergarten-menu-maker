package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.apache.commons.math3.util.Precision.round;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/28/2020
 **/
@Getter
@ToString
@EqualsAndHashCode
public class RecipeIngredientDTO {

    private static final int FAT_MULTIPLIER = 9;
    private static final int PROTEIN_MULTIPLIER = 4;
    private static final int CARBOHYDRATE_MULTIPLIER = 4;

    private final RecipeIngredient recipeIngredient;
    private double nurserySumProtein;
    private double nurserySumFat;
    private double nurserySumCarbohydrate;
    private double kindergartenSumProtein;
    private double kindergartenSumFat;
    private double kindergartenSumCarbohydrate;
    private double nurseryEnergyValue;
    private double kindergartenEnergyValue;

    @Builder(buildMethodName = "buildInternal")
    public RecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public static class RecipeIngredientDTOBuilder {
        public RecipeIngredientDTO build() {
            RecipeIngredientDTO recipeIngredientDTO = buildInternal();
            recipeIngredientDTO.calculateEnergyValue();
            return recipeIngredientDTO;
        }
    }

    public void calculateEnergyValue() {
        calculateNurseryProteins();
        calculateNurseryFats();
        calculateNurseryCarbohydrates();
        calculateKindergartenProteins();
        calculateKindergartenFats();
        calculateKindergartenCarbohydrates();
        calculateNurseryEnergyValue();
        calculateKindergartenEnergyValue();
    }

    private void calculateKindergartenEnergyValue() {
        kindergartenEnergyValue = round(kindergartenSumCarbohydrate + kindergartenSumProtein + kindergartenSumFat, 2);
    }

    private void calculateNurseryEnergyValue() {
        nurseryEnergyValue = round(nurserySumCarbohydrate + nurserySumProtein + nurserySumFat, 2);
    }

    private void calculateKindergartenCarbohydrates() {
        Quantity quantity = recipeIngredient.getKindergartenQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            kindergartenSumCarbohydrate = round(ingredient.getCarbohydrate() * quantity.getAmountNet()
                    * CARBOHYDRATE_MULTIPLIER, 2);
        }
    }

    private void calculateKindergartenFats() {
        Quantity quantity = recipeIngredient.getKindergartenQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            kindergartenSumFat = round(ingredient.getFat() * quantity.getAmountNet()
                    * FAT_MULTIPLIER, 2);
        }
    }

    private void calculateKindergartenProteins() {
        Quantity quantity = recipeIngredient.getKindergartenQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            kindergartenSumProtein = round(ingredient.getProtein() * quantity.getAmountNet()
                    * PROTEIN_MULTIPLIER, 2);
        }
    }

    private void calculateNurseryCarbohydrates() {
        Quantity quantity = recipeIngredient.getNurseryQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            nurserySumCarbohydrate = round(ingredient.getCarbohydrate() * quantity.getAmountNet()
                    * CARBOHYDRATE_MULTIPLIER, 2);
        }
    }

    private void calculateNurseryFats() {
        Quantity quantity = recipeIngredient.getNurseryQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            nurserySumFat = round(ingredient.getFat() * quantity.getAmountNet()
                    * FAT_MULTIPLIER, 2);
        }
    }

    private void calculateNurseryProteins() {
        Quantity quantity = recipeIngredient.getNurseryQuantity();
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (quantity != null && ingredient != null) {
            nurserySumProtein = round(ingredient.getProtein() * quantity.getAmountNet()
                    * PROTEIN_MULTIPLIER, 2);
        }
    }
}
