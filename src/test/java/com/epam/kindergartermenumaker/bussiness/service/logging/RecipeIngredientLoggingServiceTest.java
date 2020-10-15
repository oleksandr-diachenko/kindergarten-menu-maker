package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientLoggingServiceTest {

    @InjectMocks
    private RecipeIngredientLoggingService service;

    @Mock
    private RecipeIngredientRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();
        when(repository.save(recipeIngredient)).thenReturn(recipeIngredient);

        RecipeIngredient actual = service.save(recipeIngredient);

        assertThat(actual.getId()).isEqualTo(1);
        verify(repository).save(recipeIngredient);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();
        when(repository.findById(1L)).thenReturn(of(recipeIngredient));

        Optional<RecipeIngredient> actual = service.findById(1);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<RecipeIngredient> actual = service.findById(1);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnOneRecipeIngredientWhenFindOne() {
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();
        when(repository.findAll()).thenReturn(List.of(recipeIngredient));

        List<RecipeIngredient> actual = service.findAll();

        assertThat(actual).containsExactly(recipeIngredient);
    }

    @Test
    void shouldReturnEmptyListWhenRecipeIngredientsNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<RecipeIngredient> actual = service.findAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnOneRecipeIngredientForRecipe() {
        Recipe friedPotatoes = TestData.recipe();
        RecipeIngredient recipeIngredient = TestData.recipeIngredient();
        when(repository.findByRecipe(friedPotatoes)).thenReturn(List.of(recipeIngredient));

        List<RecipeIngredient> actual = service.findByRecipe(friedPotatoes);

        assertThat(actual).containsExactly(recipeIngredient);
    }

    @Test
    void shouldNotReturnRecipeIngredientWhenNotFoundForRecipe() {
        Recipe friedPotatoes = TestData.recipe();
        when(repository.findByRecipe(friedPotatoes)).thenReturn(List.of());

        List<RecipeIngredient> actual = service.findByRecipe(friedPotatoes);

        assertThat(actual).isEmpty();
    }
}
