package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

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
class RecipeRepositoryTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String FRIED_POTATOES_DESCRIPTION = "Fried potatoes in a skillet";
    private static final String MAIN_COURSE = "Main course";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void shouldReturnQuantityWhenPersisted() {
        Category mainCourse = prepareCategory();
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(mainCourse)
                .build();
        manager.persistAndFlush(friedPotatoes);

        Optional<Recipe> actual = recipeRepository.findById(friedPotatoes.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(FRIED_POTATOES);
        assertThat(actual.get().getDescription()).isEqualTo(FRIED_POTATOES_DESCRIPTION);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Recipe recipe = Recipe.builder()
                .name(null)
                .description(FRIED_POTATOES_DESCRIPTION)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(recipe))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsEmpty() {
        Recipe recipe = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(null)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(recipe))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }

    @Test
    void shouldReturnTrueWhenRecipeExistsByName() {
        Category dummy = Category.builder().name("Dummy").build();
        manager.persistAndFlush(dummy);
        Recipe recipe = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(dummy)
                .build();
        manager.persistAndFlush(recipe);

        boolean exists = recipeRepository.existsByName(FRIED_POTATOES);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenRecipeNotExistsByName() {
        Category dummy = Category.builder().name("Dummy").build();
        manager.persistAndFlush(dummy);
        Recipe recipe = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(dummy)
                .build();
        manager.persistAndFlush(recipe);

        boolean exists = recipeRepository.existsByName("qwe");

        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnRecipeByCategoryWhenPersisted() {
        Category mainCourse = prepareCategory();
        Recipe recipe = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(mainCourse)
                .build();
        manager.persistAndFlush(recipe);

        List<Recipe> actual = recipeRepository.findByCategory(mainCourse);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyRecipeByCategoryWhenPersisted() {
        Category mainCourse = prepareCategory();
        Category dummy = Category.builder().name("Dummy").build();
        manager.persistAndFlush(dummy);
        Recipe recipe = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .category(mainCourse)
                .build();
        manager.persistAndFlush(recipe);

        List<Recipe> actual = recipeRepository.findByCategory(dummy);

        assertThat(actual).isEmpty();
    }

    private Category prepareCategory() {
        Category category = Category.builder()
                .name(MAIN_COURSE)
                .build();
        manager.persistAndFlush(category);
        return category;
    }
}