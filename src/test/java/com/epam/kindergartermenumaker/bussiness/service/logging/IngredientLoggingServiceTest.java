package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.repository.IngredientRepository;
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
class IngredientLoggingServiceTest {

    private static final String SALT = "Salt";
    private static final long TEN = 10;

    @InjectMocks
    private IngredientLoggingService service;
    @Mock
    private IngredientRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Ingredient salt = Ingredient.builder()
                .name(SALT)
                .build();

        service.save(salt);

        verify(repository).save(salt);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Ingredient salt = Ingredient.builder()
                .id(TEN)
                .name(SALT)
                .build();
        when(repository.findById(TEN)).thenReturn(Optional.of(salt));

        Optional<Ingredient> actual = service.findById(TEN);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Ingredient> actual = service.findById(TEN);

        assertThat(actual).isEmpty();
    }
}
