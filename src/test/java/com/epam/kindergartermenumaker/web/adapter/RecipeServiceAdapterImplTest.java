package com.epam.kindergartermenumaker.web.adapter;

import com.epam.kindergartermenumaker.bussiness.service.logging.*;
import com.epam.kindergartermenumaker.dao.entity.*;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    public static final int RECIPE_ID = 1;
    public static final String MAIN_SOURCE = "Main source";
    public static final int RECIPE_INGREDIENT_ID = 2;

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
    @Mock
    private Converter<Recipe, RecipeForm> recipeToFormConverter;

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
        when(measurementService.findByDescription(GRAM)).thenReturn(of(measurement));

        recipeServiceAdapter.save(recipeForm);

        verify(measurementService, never()).save(any());
    }

    @Test
    void shouldCreateMeasurementWhenNotExistByDescription() {
        when(measurementService.findByDescription(GRAM)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm);

        Measurement measurement = Measurement.builder().description(GRAM).build();
        verify(measurementService).save(measurement);
    }

    @Test
    void shouldReturnExistingCategoryWhenExistByName() {
        Category category = Category.builder().name(MAIN_COURSE).build();
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(of(category));

        recipeServiceAdapter.save(recipeForm);

        verify(categoryService, never()).save(any());
    }

    @Test
    void shouldCreateCategoryWhenNotExistByName() {
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm);

        Category category = Category.builder().name(MAIN_COURSE).build();
        verify(categoryService).save(category);
    }

    @Test
    void shouldReturnExistingRecipeWhenExistByName() {
        Recipe recipe = Recipe.builder().name(FRIED_POTATOES).build();
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe));

        recipeServiceAdapter.save(recipeForm);

        verify(recipeService, never()).save(any());
    }

    @Test
    void shouldCreateRecipeWhenNotExistByName() {
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm);

        Recipe recipe = Recipe.builder().name(FRIED_POTATOES).description(FRIED_POTATOES_IN_A_SKILLET).build();
        verify(recipeService).save(recipe);
    }

    @Test
    void shouldReturnExistingQuantityWhenExistByNetAndGross() {
        Quantity quantity = Quantity.builder().amountNet(NURSERY_NET_AMOUNT).amountGross(NURSERY_GROSS_AMOUNT).build();
        when(quantityService.findByAmountNetAndAmountGross(NURSERY_NET_AMOUNT, NURSERY_GROSS_AMOUNT))
                .thenReturn(of(quantity));
        when(quantityService.findByAmountNetAndAmountGross(KINDERGARTEN_NET_AMOUNT, KINDERGARTEN_GROSS_AMOUNT))
                .thenReturn(of(quantity));

        recipeServiceAdapter.save(recipeForm);

        verify(quantityService, never()).save(any());
    }

    @Test
    void shouldCreateQuantityWhenNotExistByName() {
        when(quantityService.findByAmountNetAndAmountGross(NURSERY_NET_AMOUNT, NURSERY_GROSS_AMOUNT))
                .thenReturn(empty());
        when(quantityService.findByAmountNetAndAmountGross(KINDERGARTEN_NET_AMOUNT, KINDERGARTEN_GROSS_AMOUNT))
                .thenReturn(empty());

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
                .thenReturn(of(ingredient));

        recipeServiceAdapter.save(recipeForm);

        verify(ingredientService, never()).save(any());
    }

    @Test
    void shouldCreateIngredientWhenNotExistByName() {
        when(ingredientService.findByName(POTATO)).thenReturn(empty());

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
        when(recipeService.findByName(anyString())).thenReturn(of(recipe));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(of(quantity));
        when(ingredientService.findByName(anyString())).thenReturn(of(ingredient));
        when(measurementService.findByDescription(anyString())).thenReturn(of(measurement));

        recipeServiceAdapter.save(recipeForm);

        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .kindergartenQuantity(quantity)
                .nurseryQuantity(quantity)
                .ingredient(ingredient)
                .measurement(measurement).build();
        verify(recipeIngredientService).save(recipeIngredient);
    }

    @Test
    void shouldUpdateCategoryWhenCategoryExists() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(RECIPE_ID);
        recipeForm.setCategoryName(MAIN_SOURCE);
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(RECIPE_ID)).thenReturn(of(recipe));
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        when(categoryService.findByName(MAIN_SOURCE)).thenReturn(of(mainSource));

        recipeServiceAdapter.update(recipeForm);

        verify(categoryService, never()).save(any());
        verify(categoryService).findByName(MAIN_SOURCE);
        assertThat(recipe.getCategory()).isEqualTo(mainSource);
    }

    @Test
    void shouldUpdateCategoryWhenCategoryNotExists() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(RECIPE_ID);
        recipeForm.setCategoryName(MAIN_SOURCE);
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(RECIPE_ID)).thenReturn(of(recipe));
        when(categoryService.findByName(MAIN_SOURCE)).thenReturn(empty());
        Category drink = Category.builder().name(MAIN_SOURCE).build();
        when(categoryService.save(drink)).thenReturn(drink);

        recipeServiceAdapter.update(recipeForm);

        verify(categoryService).save(drink);
        verify(categoryService).findByName(MAIN_SOURCE);
        assertThat(recipe.getCategory()).isEqualTo(drink);
    }

    @Test
    void shouldUpdateRecipeWhenNameNotChanged() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(RECIPE_ID);
        recipeForm.setRecipeName(FRIED_POTATOES);
        recipeForm.setRecipeDescription(FRIED_POTATOES_IN_A_SKILLET);
        recipeForm.setCategoryName(MAIN_COURSE);
        Category category = Category.builder().name(MAIN_COURSE).build();
        Recipe recipe = Recipe.builder().id(RECIPE_ID).description(FRIED_POTATOES_IN_A_SKILLET).category(category).build();
        when(categoryService.findByName(anyString())).thenReturn(of(category));
        when(recipeService.findById(RECIPE_ID)).thenReturn(of(recipe));
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe));

        recipeServiceAdapter.update(recipeForm);

        verify(recipeService).save(recipe);
    }

    @Test
    void shouldThrowExceptionOnUpdateRecipeWhenRecipeExists() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeId(RECIPE_ID);
        recipeForm.setRecipeName(FRIED_POTATOES);
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(RECIPE_ID)).thenReturn(of(recipe));
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe));

        assertThatThrownBy(() -> recipeServiceAdapter.update(recipeForm))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotUpdateRecipeIngredientWhenNotPresentById() {
        RecipeForm recipeForm = new RecipeForm();
        IngredientForm ingredientForm = new IngredientForm();
        recipeForm.setIngredients(List.of(ingredientForm));
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(anyLong())).thenReturn(of(recipe));
        when(recipeIngredientService.findById(anyLong())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm);

        verify(ingredientService, never()).save(any());
        verify(measurementService, never()).save(any());
        verify(quantityService, never()).save(any());
        verify(recipeIngredientService, never()).save(any());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenRecipeIngredientExists() {
        RecipeForm recipeForm = new RecipeForm();
        IngredientForm ingredientForm = prepareIngredientForm();
        recipeForm.setIngredients(List.of(ingredientForm));
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(anyLong())).thenReturn(of(recipe));
        Measurement gram = Measurement.builder().description("Грам").build();
        Quantity kindergartenQuantity = Quantity.builder().amountGross(2).amountNet(1).build();
        Quantity nurseryQuantity = Quantity.builder().amountGross(4).amountNet(3).build();
        Ingredient potato = Ingredient.builder().name(POTATO).protein(5).fat(6).carbohydrate(7).build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .measurement(gram)
                .kindergartenQuantity(kindergartenQuantity)
                .nurseryQuantity(nurseryQuantity)
                .ingredient(potato)
                .build();
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient));
        when(measurementService.findByDescription("Грам")).thenReturn(of(gram));
        when(quantityService.findByAmountNetAndAmountGross(1, 2)).thenReturn(of(kindergartenQuantity));
        when(quantityService.findByAmountNetAndAmountGross(3, 4)).thenReturn(of(nurseryQuantity));
        when(ingredientService.findByName(POTATO)).thenReturn(of(potato));

        recipeServiceAdapter.update(recipeForm);

        verify(measurementService, never()).save(any());
        verify(quantityService, never()).save(any());
        verify(ingredientService, never()).save(any());
        verify(recipeIngredientService).save(recipeIngredient);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenMeasurementExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Measurement gram = Measurement.builder().description("Грам").build();
        when(measurementService.findByDescription(anyString())).thenReturn(of(gram));

        recipeServiceAdapter.update(recipeForm);

        verify(measurementService, never()).save(gram);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenMeasurementNotExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);

        when(measurementService.findByDescription(anyString())).thenReturn(empty());
        Measurement gram = Measurement.builder().description("Грам").build();

        recipeServiceAdapter.update(recipeForm);

        verify(measurementService).save(gram);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenNurseryQuantityExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Quantity nurseryQuantity = Quantity.builder().amountGross(4).amountNet(3).build();
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(of(nurseryQuantity));

        recipeServiceAdapter.update(recipeForm);

        verify(quantityService, never()).save(nurseryQuantity);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenNurseryQuantityNotExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Quantity nurseryQuantity = Quantity.builder().amountGross(4).amountNet(3).build();
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm);

        verify(quantityService).save(nurseryQuantity);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenKindergartenQuantityExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Quantity kindergartenQuantity = Quantity.builder().amountGross(2).amountNet(1).build();
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(of(kindergartenQuantity));

        recipeServiceAdapter.update(recipeForm);

        verify(quantityService, never()).save(kindergartenQuantity);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenKindergartenQuantityNotExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Quantity kindergartenQuantity = Quantity.builder().amountGross(2).amountNet(1).build();
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm);

        verify(quantityService).save(kindergartenQuantity);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenIngredientExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Ingredient ingredient = Ingredient.builder().name(POTATO).protein(5).fat(6).carbohydrate(7).build();
        when(ingredientService.findByName(POTATO)).thenReturn(of(ingredient));

        recipeServiceAdapter.update(recipeForm);

        verify(ingredientService, never()).save(ingredient);
    }

    @Test
    void shouldUpdateRecipeIngredientWhenIngredientNotExists() {
        RecipeForm recipeForm = new RecipeForm();
        prepareDataForUpdate(recipeForm);
        Ingredient ingredient = Ingredient.builder().name(POTATO).protein(5).fat(6).carbohydrate(7).build();
        when(ingredientService.findByName(POTATO)).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm);

        verify(ingredientService).save(ingredient);
    }

    @Test
    void shouldReturnEmptyRecipeFormWhenRecipeNotExists() {
        when(recipeService.findByName(anyString())).thenReturn(empty());

        RecipeForm actual = recipeServiceAdapter.findByRecipeName(FRIED_POTATOES);

        assertThat(actual).isEqualTo(new RecipeForm());
    }

    @Test
    void shouldReturnRecipeFormWhenRecipeExists() {
        Recipe friedPotatoes = Recipe.builder().name(FRIED_POTATOES).build();
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(friedPotatoes));
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setRecipeName(FRIED_POTATOES);
        when(recipeToFormConverter.convert(friedPotatoes)).thenReturn(recipeForm);

        RecipeForm actual = recipeServiceAdapter.findByRecipeName(FRIED_POTATOES);

        assertThat(actual).isEqualTo(recipeForm);
    }

    private void prepareDataForUpdate(RecipeForm recipeForm) {
        IngredientForm ingredientForm = prepareIngredientForm();
        recipeForm.setIngredients(List.of(ingredientForm));
        Recipe recipe = Recipe.builder().build();
        when(recipeService.findById(anyLong())).thenReturn(of(recipe));
        RecipeIngredient recipeIngredient = RecipeIngredient.builder().recipe(recipe).build();
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient));
    }

    private IngredientForm prepareIngredientForm() {
        IngredientForm ingredientForm = new IngredientForm();
        ingredientForm.setRecipeIngredientId(RECIPE_INGREDIENT_ID);
        ingredientForm.setIngredientName(POTATO);
        ingredientForm.setKindergartenGrossAmount(2);
        ingredientForm.setKindergartenNetAmount(1);
        ingredientForm.setNurseryGrossAmount(4);
        ingredientForm.setNurseryNetAmount(3);
        ingredientForm.setProtein(5);
        ingredientForm.setFat(6);
        ingredientForm.setCarbohydrate(7);
        return ingredientForm;
    }
}