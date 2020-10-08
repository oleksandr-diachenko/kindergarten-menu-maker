package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/8/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientToFormConverterTest {

    private static final String POTATO = "Potato";

    @InjectMocks
    private RecipeIngredientToFormConverter converter;

    @Test
    void shouldConvertRecipeIngredientToForm() {
        Ingredient potato = Ingredient.builder().name(POTATO).carbohydrate(2).fat(3).protein(4).build();
        Quantity kindergartenQuantity = Quantity.builder().amountNet(5).amountGross(6).build();
        Quantity nurseryQuantity = Quantity.builder().amountNet(7).amountGross(8).build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().id(1)
                .ingredient(potato)
                .nurseryQuantity(nurseryQuantity)
                .kindergartenQuantity(kindergartenQuantity).build();

        IngredientForm ingredientForm = converter.convert(recipeIngredient);

        assertThat(ingredientForm.getRecipeIngredientId()).isEqualTo(1);
        assertThat(ingredientForm.getCarbohydrate()).isEqualTo(2);
        assertThat(ingredientForm.getFat()).isEqualTo(3);
        assertThat(ingredientForm.getProtein()).isEqualTo(4);
        assertThat(ingredientForm.getIngredientName()).isEqualTo(POTATO);
        assertThat(ingredientForm.getKindergartenNetAmount()).isEqualTo(5);
        assertThat(ingredientForm.getKindergartenGrossAmount()).isEqualTo(6);
        assertThat(ingredientForm.getNurseryNetAmount()).isEqualTo(7);
        assertThat(ingredientForm.getNurseryGrossAmount()).isEqualTo(8);
        assertThat(ingredientForm.getMeasurementDescription()).isEqualTo("Грам");
    }
}