package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static ch.qos.logback.classic.Level.INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verify;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@ExtendWith(MockitoExtension.class)
class IngredientLoggingServiceTest {

    private static final String SALT = "Salt";

    @InjectMocks
    private IngredientLoggingService service;

    @Mock
    private IngredientRepository repository;
    private final ListAppender<ILoggingEvent> appenders = Appender.getAppenders(IngredientLoggingService.class);

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Ingredient salt = Ingredient.builder()
                .name(SALT)
                .build();

        service.save(salt);

        verify(repository).save(salt);
        assertThat(appenders.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(tuple(salt + " was saved", INFO));
    }
}
