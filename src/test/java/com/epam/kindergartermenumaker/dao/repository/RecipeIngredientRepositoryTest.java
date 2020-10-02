package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.epam.kindergartermenumaker.dao.ConstraintViolationExceptionMessage.NOT_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("integration")
class RecipeIngredientRepositoryTest {

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
    private static final String MAIN_COURSE = "Main course";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Test
    void shouldReturnFriedPotatoesIngredientsWhenPersisted() {
        Category mainCourse = prepareCategory();
        Recipe friedPotatoes = prepareFriedPotatoes(mainCourse);

        List<RecipeIngredient> actual = recipeIngredientRepository.findByRecipe(friedPotatoes);

        assertThat(actual).hasSize(4);
    }

    @Test
    void shouldThrowExceptionWhenRecipeIsNull() {
        Ingredient potato = prepareIngredient(POTATO);
        Quantity one = prepareQuantity(ONE, ONE);
        Measurement gram = prepareMeasurement(GRAM);

        assertThatThrownBy(() -> prepareRecipeIngredient(null, potato, one, gram))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIngredientIsNull() {
        Category mainCourse = prepareCategory();
        Recipe friedPotatoes = prepareFriedPotatoRecipe(mainCourse);
        Quantity one = prepareQuantity(ONE, ONE);
        Measurement gram = prepareMeasurement(GRAM);

        assertThatThrownBy(() -> prepareRecipeIngredient(friedPotatoes, null, one, gram))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNull() {
        Category mainCourse = prepareCategory();
        Recipe friedPotatoes = prepareFriedPotatoRecipe(mainCourse);
        Ingredient potato = prepareIngredient(POTATO);
        Measurement gram = prepareMeasurement(GRAM);

        assertThatThrownBy(() -> prepareRecipeIngredient(friedPotatoes, potato, null, gram))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMeasurementIsNull() {
        Category mainCourse = prepareCategory();
        Recipe friedPotatoes = prepareFriedPotatoRecipe(mainCourse);
        Ingredient potato = prepareIngredient(POTATO);
        Quantity one = prepareQuantity(ONE, ONE);

        assertThatThrownBy(() -> prepareRecipeIngredient(friedPotatoes, potato, one, null))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    private Recipe prepareFriedPotatoes(Category category) {
        Recipe friedPotatoes = prepareFriedPotatoRecipe(category);

        Ingredient potato = prepareIngredient(POTATO);
        Ingredient salt = prepareIngredient(SALT);
        Ingredient pepper = prepareIngredient(PEPPER);
        Ingredient oil = prepareIngredient(OIL);

        Measurement gram = prepareMeasurement(GRAM);
        Measurement ml = prepareMeasurement(MILLILITER);

        Quantity one = prepareQuantity(ONE, ONE);
        Quantity ten = prepareQuantity(TEN, TEN);
        Quantity five = prepareQuantity(FIVE, FIVE);
        Quantity fifty = prepareQuantity(FIFTY, FIFTY);

        prepareRecipeIngredient(friedPotatoes, potato, one, gram);
        prepareRecipeIngredient(friedPotatoes, salt, ten, gram);
        prepareRecipeIngredient(friedPotatoes, pepper, five, gram);
        prepareRecipeIngredient(friedPotatoes, oil, fifty, ml);

        return friedPotatoes;
    }

    private void prepareRecipeIngredient(Recipe recipe, Ingredient ingredient, Quantity quantity, Measurement measurement) {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .kindergartenQuantity(quantity)
                .nurseryQuantity(quantity)
                .measurement(measurement)
                .build();
        manager.persistAndFlush(recipeIngredient);
    }

    private Measurement prepareMeasurement(String description) {
        Measurement measurement = Measurement.builder().description(description).build();
        manager.persistAndFlush(measurement);
        return measurement;
    }

    private Quantity prepareQuantity(int amountNet, int amountGross) {
        Quantity quantity = Quantity.builder().amountNet(amountNet).amountGross(amountGross).build();
        manager.persistAndFlush(quantity);
        return quantity;
    }

    private Ingredient prepareIngredient(String name) {
        Ingredient ingredient = Ingredient.builder().name(name).build();
        manager.persistAndFlush(ingredient);
        return ingredient;
    }

    private Recipe prepareFriedPotatoRecipe(Category category) {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_IN_A_SKILLET)
                .category(category)
                .build();
        manager.persistAndFlush(friedPotatoes);
        return friedPotatoes;
    }

    private Category prepareCategory() {
        Category category = Category.builder()
                .name(MAIN_COURSE)
                .build();
        manager.persistAndFlush(category);
        return category;
    }
}