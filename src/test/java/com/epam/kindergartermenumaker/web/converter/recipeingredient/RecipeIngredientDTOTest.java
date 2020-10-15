package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static com.epam.kindergartermenumaker.TestData.*;
import static com.epam.kindergartermenumaker.TestData.nursery;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
class RecipeIngredientDTOTest {

    @Test
    void shouldCalculateMineralsForRecipeIngredient() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(2).protein(3).fat(4).build();
        Quantity nurseryQuantity = Quantity.builder().amountNet(5).build();
        Quantity kindergartenQuantity = Quantity.builder().amountNet(7).build();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, nurseryQuantity, kindergartenQuantity);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isEqualTo(4 * 5);
        assertThat(recipeIngredientDTO.getNurserySumProtein()).isEqualTo(3 * 5);
        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isEqualTo(2 * 5);
        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isEqualTo(4 * 7);
        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isEqualTo(3 * 7);
        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isEqualTo(2 * 7);

        assertThat(recipeIngredientDTO.getNurseryEnergyValue()).isEqualTo(280);
        assertThat(recipeIngredientDTO.getKindergartenEnergyValue()).isEqualTo(392);
    }

    @Test
    void shouldNotCalculateNurseryFatWhenIngredientNull() {
        Quantity nurseryQuantity = nursery();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryFatWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryProteinWhenIngredientNull() {
        Quantity nurseryQuantity = nursery();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryProteinWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryCarbohydrateWhenIngredientNull() {
        Quantity nurseryQuantity = nursery();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, nurseryQuantity, null);

        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryCarbohydrateWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getNurserySumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenFatWhenIngredientNull() {
        Quantity kindergartenQuantity = kindergarten();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenFatWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumFat()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryKindergartenProteinWhenIngredientNull() {
        Quantity kindergartenQuantity = kindergarten();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateNurseryKindergartenProteinWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumProtein()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenCarbohydrateWhenIngredientNull() {
        Quantity kindergartenQuantity = kindergarten();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(null, kindergartenQuantity, null);

        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isZero();
    }

    @Test
    void shouldNotCalculateKindergartenCarbohydrateWhenQuantityNull() {
        Ingredient ingredient = ingredient();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, null, null);

        assertThat(recipeIngredientDTO.getKindergartenSumCarbohydrate()).isZero();
    }

    @Test
    void shouldSetEnergyValueAsZeroWhenAllMineralsIsZero() {
        Ingredient ingredient = Ingredient.builder().carbohydrate(0).protein(0).fat(0).build();
        Quantity nurseryQuantity = nursery();
        Quantity kindergartenQuantity = kindergarten();

        RecipeIngredientDTO recipeIngredientDTO = buildRecipeIngredientDTO(ingredient, nurseryQuantity, kindergartenQuantity);

        assertThat(recipeIngredientDTO.getKindergartenEnergyValue()).isZero();
        assertThat(recipeIngredientDTO.getNurseryEnergyValue()).isZero();
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