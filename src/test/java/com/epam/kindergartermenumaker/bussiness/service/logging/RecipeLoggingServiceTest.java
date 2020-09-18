package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
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
class RecipeLoggingServiceTest {

    private static final String FRIED_POTATOES = "Fried potatoes";

    @InjectMocks
    private RecipeLoggingService service;

    @Mock
    private RecipeRepository repository;
    private final ListAppender<ILoggingEvent> appenders = Appender.getAppenders(RecipeLoggingService.class);

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .build();

        service.save(friedPotatoes);

        verify(repository).save(friedPotatoes);
        assertThat(appenders.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(tuple(friedPotatoes + " was saved", INFO));
    }
}
