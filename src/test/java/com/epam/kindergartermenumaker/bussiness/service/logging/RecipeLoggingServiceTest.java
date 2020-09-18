package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
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
class RecipeLoggingServiceTest {

    private static final String FRIED_POTATOES = "Fried potatoes";

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
}
