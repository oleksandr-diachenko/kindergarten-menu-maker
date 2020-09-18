package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
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
class RecipeIngredientLoggingServiceTest {

    @InjectMocks
    private RecipeIngredientLoggingService service;

    @Mock
    private RecipeIngredientRepository repository;
    private final ListAppender<ILoggingEvent> appenders = Appender.getAppenders(RecipeIngredientLoggingService.class);

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        RecipeIngredient dummy = RecipeIngredient.builder()
                .id(1)
                .build();

        service.save(dummy);

        verify(repository).save(dummy);
        assertThat(appenders.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(tuple(dummy + " was saved", INFO));
    }
}
