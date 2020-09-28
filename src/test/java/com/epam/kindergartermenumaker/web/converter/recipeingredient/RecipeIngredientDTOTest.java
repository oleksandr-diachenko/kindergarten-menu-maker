package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
class RecipeIngredientDTOTest {

    @Test
    void shouldCalculateEnergyValueForRecipeIngredient() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();
        Quantity nurseryQuantity = Quantity.builder().amountNet(5).build();
        Quantity kindergartenQuantity = Quantity.builder().amountNet(7).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, nurseryQuantity, kindergartenQuantity);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isEqualTo(4 * 5 * 9);
        assertThat(recipeIngredientDTO.getNurserySumProtein()).isEqualTo(3 * 5 * 4);
        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isEqualTo(2 * 5 * 4);
        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isEqualTo(4 * 7 * 9);
        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isEqualTo(3 * 7 * 4);
        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isEqualTo(2 * 7 * 4);
    }

    @Test
    void shouldNotCalculateNurseryFatWhenIngredientNull() {
        Quantity nurseryQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryFatWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryProteinWhenIngredientNull() {
        Quantity nurseryQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryProteinWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryCarbohydrateWhenIngredientNull() {
        Quantity nurseryQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryCarbohydrateWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenFatWhenIngredientNull() {
        Quantity kindergartenQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenFatWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryKindergartenProteinWhenIngredientNull() {
        Quantity kindergartenQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryKindergartenProteinWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenCarbohydrateWhenIngredientNull() {
        Quantity kindergartenQuantity = Quantity.builder().amountNet(5).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenCarbohydrateWhenQuantityNull() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isZero();
    }

    private RecipeIngredientDTO buildRecipeIngredientDTO(Ingredient ingredient, Quantity nurseryQuantity, Quantity kindergartenQuantity) {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .ingredient(ingredient)
                .nurseryQuantity(nurseryQuantity)
                .kindergartenQuantity(kindergartenQuantity)
                .build();
        return RecipeIngredientDTO.builder()
                .recipeIngredient(recipeIngredient)
                .build();
    }
}