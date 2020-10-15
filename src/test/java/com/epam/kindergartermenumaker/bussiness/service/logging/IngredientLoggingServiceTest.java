package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.epam.kindergartermenumaker.TestData.POTATO;
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
class IngredientLoggingServiceTest {

    @InjectMocks
    private IngredientLoggingService service;
    @Mock
    private IngredientRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Ingredient potato = TestData.ingredient();
        when(repository.save(potato)).thenReturn(potato);

        Ingredient actual = service.save(potato);

        assertThat(actual.getName()).isEqualTo(POTATO);
        verify(repository).save(potato);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Ingredient potato = TestData.ingredient();
        when(repository.findById(1L)).thenReturn(of(potato));

        Optional<Ingredient> actual = service.findById(1);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Ingredient> actual = service.findById(1);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByName(POTATO)).thenReturn(true);

        boolean actual = service.existsByName(POTATO);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByName(POTATO)).thenReturn(false);

        boolean actual = service.existsByName(POTATO);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnIngredientWhenFindByNameExist() {
        Ingredient potato = TestData.ingredient();
        when(repository.findByName(POTATO)).thenReturn(of(potato));

        Optional<Ingredient> actual = service.findByName(POTATO);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(POTATO);
    }

    @Test
    void shouldReturnEmptyIngredientWhenFindByNameNotExist() {
        when(repository.findByName(POTATO)).thenReturn(empty());

        Optional<Ingredient> actual = service.findByName(POTATO);

        assertThat(actual).isEmpty();
    }
}
