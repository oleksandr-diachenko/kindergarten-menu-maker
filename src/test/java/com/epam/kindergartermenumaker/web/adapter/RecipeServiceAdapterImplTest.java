package com.epam.kindergartermenumaker.web.adapter;

import com.epam.kindergartermenumaker.bussiness.service.logging.*;
import com.epam.kindergartermenumaker.dao.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/30/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeServiceAdapterImplTest {

    private static final String FRIED_POTATOES_IN_A_SKILLET = "Fried potatoes in a skillet";
    private static final String MAIN_COURSE = "Main course";
    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String POTATO = "Potato";
    private static final String GRAM = "Gram";
    private static final int NURSERY_NET_AMOUNT = 2;
    private static final int KINDERGARTEN_NET_AMOUNT = 3;
    private static final int NURSERY_GROSS_AMOUNT = 4;
    private static final int KINDERGARTEN_GROSS_AMOUNT = 5;
    private static final int PROTEIN_AMOUNT = 6;
    private static final int FAT_AMOUNT = 7;
    private static final int CARBOHYDRATE_AMOUNT = 8;

    @InjectMocks
    private RecipeServiceAdapterImpl recipeServiceAdapter;

    @Mock
    private CategoryService categoryService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private QuantityService quantityService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private MeasurementService measurementService;
    @Mock
    private RecipeIngredientService recipeIngredientService;

    private RecipeForm recipeForm;

    @BeforeEach
    void prepareFormObject() {
        IngredientForm ingredientForm = new IngredientForm();
        ingredientForm.setIngredientName(POTATO);
        ingredientForm.setNurseryNetAmount(NURSERY_NET_AMOUNT);
        ingredientForm.setKindergartenNetAmount(KINDERGARTEN_NET_AMOUNT);
        ingredientForm.setNurseryGrossAmount(NURSERY_GROSS_AMOUNT);
        ingredientForm.setKindergartenGrossAmount(KINDERGARTEN_GROSS_AMOUNT);
        ingredientForm.setProtein(PROTEIN_AMOUNT);
        ingredientForm.setFat(FAT_AMOUNT);
        ingredientForm.setCarbohydrate(CARBOHYDRATE_AMOUNT);
        ingredientForm.setMeasurementDescription(GRAM);

        recipeForm = new RecipeForm();
        recipeForm.setCategoryName(MAIN_COURSE);
        recipeForm.setRecipeName(FRIED_POTATOES);
        recipeForm.setRecipeDescription(FRIED_POTATOES_IN_A_SKILLET);
        recipeForm.setIngredients(List.of(ingredientForm));
    }

    @Test
    void shouldReturnExistingMeasurementWhenExistByDescription() {
        Measurement measurement = Measurement.builder().description(GRAM).build();
        when(measurementService.findByDescription(GRAM)).thenReturn(Optional.of(measurement));

        recipeServiceAdapter.save(recipeForm);

        verify(measurementService, never()).save(any());
    }

    @Test
    void shouldCreateMeasurementWhenNotExistByDescription() {
        when(measurementService.findByDescription(GRAM)).thenReturn(Optional.empty());

        recipeServiceAdapter.save(recipeForm);

        Measurement measurement = Measurement.builder().description(GRAM).build();
        verify(measurementService).save(measurement);
    }

    @Test
    void shouldReturnExistingCategoryWhenExistByName() {
        Category category = Category.builder().name(MAIN_COURSE).build();
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(Optional.of(category));

        recipeServiceAdapter.save(recipeForm);

        verify(categoryService, never()).save(any());
    }

    @Test
    void shouldCreateCategoryWhenNotExistByName() {
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(Optional.empty());

        recipeServiceAdapter.save(recipeForm);

        Category category = Category.builder().name(MAIN_COURSE).build();
        verify(categoryService).save(category);
    }

    @Test
    void shouldReturnExistingRecipeWhenExistByName() {
        Recipe recipe = Recipe.builder().name(FRIED_POTATOES).build();
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(Optional.of(recipe));

        recipeServiceAdapter.save(recipeForm);

        verify(recipeService, never()).save(any());
    }

    @Test
    void shouldCreateRecipeWhenNotExistByName() {
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(Optional.empty());

        recipeServiceAdapter.save(recipeForm);

        Recipe recipe = Recipe.builder().name(FRIED_POTATOES).description(FRIED_POTATOES_IN_A_SKILLET).build();
        verify(recipeService).save(recipe);
    }

    @Test
    void shouldReturnExistingQuantityWhenExistByNetAndGross() {
        Quantity quantity = Quantity.builder().amountNet(NURSERY_NET_AMOUNT).amountGross(NURSERY_GROSS_AMOUNT).build();
        when(quantityService.findByAmountNetAndAmountGross(NURSERY_NET_AMOUNT, NURSERY_GROSS_AMOUNT))
                .thenReturn(Optional.of(quantity));
        when(quantityService.findByAmountNetAndAmountGross(KINDERGARTEN_NET_AMOUNT, KINDERGARTEN_GROSS_AMOUNT))
                .thenReturn(Optional.of(quantity));

        recipeServiceAdapter.save(recipeForm);

        verify(quantityService, never()).save(any());
    }

    @Test
    void shouldCreateQuantityWhenNotExistByName() {
        when(quantityService.findByAmountNetAndAmountGross(NURSERY_NET_AMOUNT, NURSERY_GROSS_AMOUNT))
                .thenReturn(Optional.empty());
        when(quantityService.findByAmountNetAndAmountGross(KINDERGARTEN_NET_AMOUNT, KINDERGARTEN_GROSS_AMOUNT))
                .thenReturn(Optional.empty());

        recipeServiceAdapter.save(recipeForm);

        Quantity quantityNursery = Quantity.builder()
                .amountNet(NURSERY_NET_AMOUNT).amountGross(NURSERY_GROSS_AMOUNT).build();
        Quantity quantityKindergarten = Quantity.builder()
                .amountNet(KINDERGARTEN_NET_AMOUNT).amountGross(KINDERGARTEN_GROSS_AMOUNT).build();
        verify(quantityService).save(quantityNursery);
        verify(quantityService).save(quantityKindergarten);
    }

    @Test
    void shouldReturnExistingIngredientWhenExistByName() {
        Ingredient ingredient = Ingredient.builder()
                .name(POTATO).carbohydrate(CARBOHYDRATE_AMOUNT).protein(PROTEIN_AMOUNT).fat(FAT_AMOUNT).build();
        when(ingredientService.findByName(POTATO))
                .thenReturn(Optional.of(ingredient));

        recipeServiceAdapter.save(recipeForm);

        verify(ingredientService, never()).save(any());
    }

    @Test
    void shouldCreateIngredientWhenNotExistByName() {
        when(ingredientService.findByName(POTATO)).thenReturn(Optional.empty());

        recipeServiceAdapter.save(recipeForm);

        Ingredient ingredient = Ingredient.builder()
                .name(POTATO).carbohydrate(CARBOHYDRATE_AMOUNT).protein(PROTEIN_AMOUNT).fat(FAT_AMOUNT).build();
        verify(ingredientService).save(ingredient);
    }

    @Test
    void shouldCreateRecipeIngredient() {
        Recipe recipe = Recipe.builder().build();
        Quantity quantity = Quantity.builder().build();
        Ingredient ingredient = Ingredient.builder().build();
        Measurement measurement = Measurement.builder().build();
        when(recipeService.findByName(anyString())).thenReturn(Optional.of(recipe));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(Optional.of(quantity));
        when(ingredientService.findByName(anyString())).thenReturn(Optional.of(ingredient));
        when(measurementService.findByDescription(anyString())).thenReturn(Optional.of(measurement));

        recipeServiceAdapter.save(recipeForm);

        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .kindergartenQuantity(quantity)
                .nurseryQuantity(quantity)
                .ingredient(ingredient)
                .measurement(measurement).build();
        verify(recipeIngredientService).save(recipeIngredient);
    }
}