package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
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
class MeasurementLoggingServiceTest {

    private static final String GRAM = "Gram";

    @InjectMocks
    private MeasurementLoggingService service;

    @Mock
    private MeasurementRepository repository;
    private final ListAppender<ILoggingEvent> appenders = Appender.getAppenders(MeasurementLoggingService.class);

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Measurement gram = Measurement.builder()
                .description(GRAM)
                .build();

        service.save(gram);

        verify(repository).save(gram);
        assertThat(appenders.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(tuple(gram + " was saved", INFO));
    }
}
