package com.epam.kindergartermenumaker.bussiness.service.logging;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeIngredientLoggingServiceTest {

    private static final long TEN = 10;

    @InjectMocks
    private RecipeIngredientLoggingService service;

    @Mock
    private RecipeIngredientRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(1)
                .build();

        service.save(dummy);

        verify(repository).save(dummy);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(TEN)
                .build();
        when(repository.findById(TEN)).thenReturn(Optional.of(dummy));

        Optional<RecipeIngredient> actual = service.findById(TEN);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<RecipeIngredient> actual = service.findById(TEN);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnOneRecipeIngredientWhenFindOne() {
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(TEN)
                .build();
        when(repository.findAll()).thenReturn(List.of(dummy));

        List<RecipeIngredient> actual = service.findAll();

        assertThat(actual).containsExactly(dummy);
    }

    @Test
    void shouldReturnEmptyListWhenRecipeIngredientsNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<RecipeIngredient> actual = service.findAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnOneRecipeIngredientForRecipe() {
        Recipe friedPotatoes = Recipe.builder()
                .name("Fried potatoes")
                .build();
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(TEN)
                .build();
        when(repository.findByRecipe(friedPotatoes)).thenReturn(List.of(dummy));

        List<RecipeIngredient> actual = service.findByRecipe(friedPotatoes);

        assertThat(actual).containsExactly(dummy);
    }

    @Test
    void shouldNotReturnRecipeIngredientWhenNotFoundForRecipe() {
        Recipe friedPotatoes = Recipe.builder()
                .name("Fried potatoes")
                .build();
        when(repository.findByRecipe(friedPotatoes)).thenReturn(List.of());

        List<RecipeIngredient> actual = service.findByRecipe(friedPotatoes);

        assertThat(actual).isEmpty();
    }
}
