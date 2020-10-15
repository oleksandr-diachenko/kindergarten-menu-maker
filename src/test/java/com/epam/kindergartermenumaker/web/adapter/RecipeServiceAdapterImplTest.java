package com.epam.kindergartermenumaker.web.adapter;

import com.epam.kindergartermenumaker.bussiness.service.logging.*;
import com.epam.kindergartermenumaker.dao.entity.*;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.kindergartermenumaker.TestData.*;
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

    @Test
    void shouldReturnExistingMeasurementWhenExistByDescription() {
        when(measurementService.findByDescription(GRAM)).thenReturn(of(measurement()));

        recipeServiceAdapter.save(recipeForm());

        verify(measurementService, never()).save(any());
    }

    @Test
    void shouldCreateMeasurementWhenNotExistByDescription() {
        when(measurementService.findByDescription(GRAM)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm());

        Measurement measurement = measurement();
        verify(measurementService).save(measurement);
    }

    @Test
    void shouldReturnExistingCategoryWhenExistByName() {
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(of(category()));

        recipeServiceAdapter.save(recipeForm());

        verify(categoryService, never()).save(any());
    }

    @Test
    void shouldCreateCategoryWhenNotExistByName() {
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm());

        Category category = category();
        verify(categoryService).save(category);
    }

    @Test
    void shouldReturnExistingRecipeWhenExistByName() {
        Recipe recipe = recipe();
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe));

        recipeServiceAdapter.save(recipeForm());

        verify(recipeService, never()).save(any());
    }

    @Test
    void shouldCreateRecipeWhenNotExistByName() {
        when(categoryService.findByName(any())).thenReturn(of(category()));
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm());

        verify(recipeService).save(recipe());
    }

    @Test
    void shouldReturnExistingQuantityWhenExistByNetAndGross() {
        when(quantityService.findByAmountNetAndAmountGross(nursery().getAmountNet(), nursery().getAmountGross()))
                .thenReturn(of(nursery()));
        when(quantityService.findByAmountNetAndAmountGross(kindergarten().getAmountNet(), kindergarten().getAmountGross()))
                .thenReturn(of(nursery()));

        recipeServiceAdapter.save(recipeForm());

        verify(quantityService, never()).save(any());
    }

    @Test
    void shouldCreateQuantityWhenNotExistByName() {
        when(quantityService.findByAmountNetAndAmountGross(nursery().getAmountNet(), nursery().getAmountGross()))
                .thenReturn(empty());
        when(quantityService.findByAmountNetAndAmountGross(kindergarten().getAmountNet(), kindergarten().getAmountGross()))
                .thenReturn(empty());

        recipeServiceAdapter.save(recipeForm());

        verify(quantityService).save(nursery());
        verify(quantityService).save(kindergarten());
    }

    @Test
    void shouldReturnExistingIngredientWhenExistByName() {
        when(ingredientService.findByName(POTATO))
                .thenReturn(of(ingredient()));

        recipeServiceAdapter.save(recipeForm());

        verify(ingredientService, never()).save(any());
    }

    @Test
    void shouldCreateIngredientWhenNotExistByName() {
        when(ingredientService.findByName(POTATO)).thenReturn(empty());

        recipeServiceAdapter.save(recipeForm());

        Ingredient ingredient = ingredient();
        verify(ingredientService).save(ingredient);
    }

    @Test
    void shouldCreateRecipeIngredient() {
        when(recipeService.findByName(anyString())).thenReturn(of(recipe()));
        when(quantityService.findByAmountNetAndAmountGross(nursery().getAmountNet(), nursery().getAmountGross()))
                .thenReturn(of(nursery()));
        when(quantityService.findByAmountNetAndAmountGross(kindergarten().getAmountNet(), kindergarten().getAmountGross()))
                .thenReturn(of(kindergarten()));
        when(ingredientService.findByName(anyString())).thenReturn(of(ingredient()));
        when(measurementService.findByDescription(anyString())).thenReturn(of(measurement()));

        recipeServiceAdapter.save(recipeForm());

        RecipeIngredient recipeIngredient = recipeIngredient();
        recipeIngredient.setId(0);
        verify(recipeIngredientService).save(recipeIngredient);
    }

    @Test
    void shouldUpdateCategoryWhenCategoryExists() {
        when(recipeService.findById(1)).thenReturn(of(recipe()));
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(of(category()));

        recipeServiceAdapter.update(recipeForm());

        verify(categoryService, never()).save(any());
        verify(categoryService).findByName(MAIN_COURSE);
        assertThat(recipe().getCategory()).isEqualTo(category());
    }

    @Test
    void shouldUpdateCategoryWhenCategoryNotExists() {
        when(recipeService.findById(1)).thenReturn(of(recipe()));
        when(categoryService.findByName(MAIN_COURSE)).thenReturn(empty());
        when(categoryService.save(category())).thenReturn(category());

        recipeServiceAdapter.update(recipeForm());

        verify(categoryService).save(category());
        verify(categoryService).findByName(MAIN_COURSE);
        assertThat(recipe().getCategory()).isEqualTo(category());
    }

    @Test
    void shouldUpdateRecipeWhenNameNotChanged() {
        when(categoryService.findByName(anyString())).thenReturn(of(category()));
        when(recipeService.findById(1)).thenReturn(of(recipe()));
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe()));

        recipeServiceAdapter.update(recipeForm());

        verify(recipeService).save(recipe());
    }

    @Test
    void shouldThrowExceptionOnUpdateRecipeWhenRecipeExists() {
        when(recipeService.findById(2L)).thenReturn(of(recipe()));
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe()));

        RecipeForm recipeForm = recipeForm();
        recipeForm.setRecipeId(2L);
        assertThatThrownBy(() -> recipeServiceAdapter.update(recipeForm))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotUpdateRecipeIngredientWhenNotPresentById() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm());

        verify(ingredientService, never()).save(any());
        verify(measurementService, never()).save(any());
        verify(quantityService, never()).save(any());
        verify(recipeIngredientService, never()).save(any());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenRecipeIngredientExists() {
        when(categoryService.findByName(anyString())).thenReturn(of(category()));
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(measurementService.findByDescription(measurement().getDescription())).thenReturn(of(measurement()));
        when(quantityService.findByAmountNetAndAmountGross(kindergarten().getAmountNet(), kindergarten().getAmountGross()))
                .thenReturn(of(kindergarten()));
        when(quantityService.findByAmountNetAndAmountGross(nursery().getAmountNet(), nursery().getAmountGross()))
                .thenReturn(of(nursery()));
        when(ingredientService.findByName(ingredient().getName())).thenReturn(of(ingredient()));

        recipeServiceAdapter.update(recipeForm());

        verify(measurementService, never()).save(any());
        verify(quantityService, never()).save(any());
        verify(ingredientService, never()).save(any());
        verify(recipeIngredientService).save(recipeIngredient());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenMeasurementExists() {
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(measurementService.findByDescription(anyString())).thenReturn(of(measurement()));

        recipeServiceAdapter.update(recipeForm());

        verify(measurementService, never()).save(measurement());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenMeasurementNotExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(measurementService.findByDescription(anyString())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm());

        verify(measurementService).save(measurement());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenNurseryQuantityExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(of(nursery()));

        recipeServiceAdapter.update(recipeForm());

        verify(quantityService, never()).save(nursery());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenNurseryQuantityNotExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm());

        verify(quantityService).save(nursery());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenKindergartenQuantityExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(of(kindergarten()));

        recipeServiceAdapter.update(recipeForm());

        verify(quantityService, never()).save(kindergarten());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenKindergartenQuantityNotExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(quantityService.findByAmountNetAndAmountGross(anyDouble(), anyDouble())).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm());

        verify(quantityService).save(kindergarten());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenIngredientExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(ingredientService.findByName(POTATO)).thenReturn(of(ingredient()));

        recipeServiceAdapter.update(recipeForm());

        verify(ingredientService, never()).save(ingredient());
    }

    @Test
    void shouldUpdateRecipeIngredientWhenIngredientNotExists() {
        when(recipeService.findById(anyLong())).thenReturn(of(recipe()));
        when(recipeIngredientService.findById(anyLong())).thenReturn(of(recipeIngredient()));
        when(ingredientService.findByName(POTATO)).thenReturn(empty());

        recipeServiceAdapter.update(recipeForm());

        verify(ingredientService).save(ingredient());
    }

    @Test
    void shouldReturnEmptyRecipeFormWhenRecipeNotExists() {
        when(recipeService.findByName(anyString())).thenReturn(empty());

        RecipeForm actual = recipeServiceAdapter.findByRecipeName(FRIED_POTATOES);

        assertThat(actual).isEqualTo(new RecipeForm());
    }

    @Test
    void shouldReturnRecipeFormWhenRecipeExists() {
        when(recipeService.findByName(FRIED_POTATOES)).thenReturn(of(recipe()));
        when(recipeToFormConverter.convert(recipe())).thenReturn(recipeForm());

        RecipeForm actual = recipeServiceAdapter.findByRecipeName(FRIED_POTATOES);

        assertThat(actual).isEqualTo(recipeForm());
    }
}