package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.repository.QuantityRepository;
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
class QuantityLoggingServiceTest {

    private static final int TEN = 10;

    @InjectMocks
    private QuantityLoggingService service;

    @Mock
    private QuantityRepository repository;
    private final ListAppender<ILoggingEvent> appenders = Appender.getAppenders(QuantityLoggingService.class);

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Quantity ten = Quantity.builder()
                .amount(TEN)
                .build();

        service.save(ten);

        verify(repository).save(ten);
        assertThat(appenders.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(tuple(ten + " was saved", INFO));
    }
}
