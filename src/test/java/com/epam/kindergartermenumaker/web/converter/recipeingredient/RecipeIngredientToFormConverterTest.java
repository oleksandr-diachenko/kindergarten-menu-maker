package com.epam.kindergartermenumaker.web.converter.recipeingredient;

import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.kindergartermenumaker.TestData.POTATO;
import static com.epam.kindergartermenumaker.TestData.recipeIngredient;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/8/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientToFormConverterTest {

    @InjectMocks
    private RecipeIngredientToFormConverter converter;

    @Test
    void shouldConvertRecipeIngredientToForm() {
        IngredientForm ingredientForm = converter.convert(recipeIngredient());

        assertThat(ingredientForm.getRecipeIngredientId()).isEqualTo(1);
        assertThat(ingredientForm.getCarbohydrate()).isEqualTo(4);
        assertThat(ingredientForm.getFat()).isEqualTo(3);
        assertThat(ingredientForm.getProtein()).isEqualTo(2);
        assertThat(ingredientForm.getIngredientName()).isEqualTo(POTATO);
        assertThat(ingredientForm.getKindergartenNetAmount()).isEqualTo(7);
        assertThat(ingredientForm.getKindergartenGrossAmount()).isEqualTo(8);
        assertThat(ingredientForm.getNurseryNetAmount()).isEqualTo(5);
        assertThat(ingredientForm.getNurseryGrossAmount()).isEqualTo(6);
        assertThat(ingredientForm.getMeasurementDescription()).isEqualTo("Грам");
    }
}