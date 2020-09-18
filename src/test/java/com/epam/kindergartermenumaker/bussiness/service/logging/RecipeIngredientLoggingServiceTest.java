package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
