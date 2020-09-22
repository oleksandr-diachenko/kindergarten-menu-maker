package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
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
class RecipeLoggingServiceTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final long TEN = 10;

    @InjectMocks
    private RecipeLoggingService service;

    @Mock
    private RecipeRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .build();

        service.save(friedPotatoes);

        verify(repository).save(friedPotatoes);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .build();
        when(repository.findById(TEN)).thenReturn(Optional.of(friedPotatoes));

        Optional<Recipe> actual = service.findById(TEN);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Recipe> actual = service.findById(TEN);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnAllRecipesWhenFindAllTriggered() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .build();
        when(repository.findAll()).thenReturn(List.of(friedPotatoes));

        List<Recipe> actual = service.findAll();

        assertThat(actual).containsExactly(friedPotatoes);
    }
}
