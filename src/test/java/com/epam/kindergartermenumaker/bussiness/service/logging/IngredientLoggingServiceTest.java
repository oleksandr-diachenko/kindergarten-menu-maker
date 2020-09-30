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
        when(repository.save(salt)).thenReturn(salt);

        Ingredient actual = service.save(salt);

        assertThat(actual.getName()).isEqualTo(SALT);
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

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByName(SALT)).thenReturn(true);

        boolean actual = service.existsByName(SALT);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByName(SALT)).thenReturn(false);

        boolean actual = service.existsByName(SALT);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnIngredientWhenFindByNameExist() {
        Ingredient salt = Ingredient.builder()
                .id(TEN)
                .name(SALT)
                .build();
        when(repository.findByName(SALT)).thenReturn(Optional.ofNullable(salt));

        Optional<Ingredient> actual = service.findByName(SALT);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(SALT);
    }

    @Test
    void shouldReturnEmptyIngredientWhenFindByNameNotExist() {
        when(repository.findByName(SALT)).thenReturn(Optional.empty());

        Optional<Ingredient> actual = service.findByName(SALT);

        assertThat(actual).isEmpty();
    }
}
