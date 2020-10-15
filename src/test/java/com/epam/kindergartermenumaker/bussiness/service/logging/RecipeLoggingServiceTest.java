package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.kindergartermenumaker.TestData.FRIED_POTATOES;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class RecipeLoggingServiceTest {

    @InjectMocks
    private RecipeLoggingService service;

    @Mock
    private RecipeRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Recipe friedPotatoes = TestData.recipe();
        when(repository.save(friedPotatoes)).thenReturn(friedPotatoes);

        Recipe actual = service.save(friedPotatoes);

        assertThat(actual.getName()).isEqualTo(FRIED_POTATOES);
        verify(repository).save(friedPotatoes);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .build();
        when(repository.findById(1L)).thenReturn(of(friedPotatoes));

        Optional<Recipe> actual = service.findById(1);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Recipe> actual = service.findById(1);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnAllRecipesWhenFindAllTriggered() {
        Recipe friedPotatoes = TestData.recipe();
        when(repository.findAll()).thenReturn(List.of(friedPotatoes));

        List<Recipe> actual = service.findAll();

        assertThat(actual).containsExactly(friedPotatoes);
    }

    @Test
    void shouldReturnAllRecipesByCategoryWhenFindByCategoryTriggered() {
        Recipe friedPotatoes = TestData.recipe();
        Category mainSource = TestData.category();
        when(repository.findByCategory(mainSource)).thenReturn(List.of(friedPotatoes));

        List<Recipe> actual = service.findByCategory(mainSource);

        assertThat(actual).containsExactly(friedPotatoes);
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByName(FRIED_POTATOES)).thenReturn(true);

        boolean actual = service.existsByName(FRIED_POTATOES);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByName(FRIED_POTATOES)).thenReturn(false);

        boolean actual = service.existsByName(FRIED_POTATOES);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnRecipeWhenFindByNameExist() {
        Recipe friedPotatoes = TestData.recipe();
        when(repository.findByName(FRIED_POTATOES)).thenReturn(of(friedPotatoes));

        Optional<Recipe> actual = service.findByName(FRIED_POTATOES);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(FRIED_POTATOES);
    }

    @Test
    void shouldReturnEmptyRecipeWhenFindByNameNotExist() {
        when(repository.findByName(FRIED_POTATOES)).thenReturn(empty());

        Optional<Recipe> actual = service.findByName(FRIED_POTATOES);

        assertThat(actual).isEmpty();
    }
}
