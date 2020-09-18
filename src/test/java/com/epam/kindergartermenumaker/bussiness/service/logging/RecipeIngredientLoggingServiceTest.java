package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

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
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(1)
                .build();

        service.save(dummy);

        verify(repository).save(dummy);
    }
}
