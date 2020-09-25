package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
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
class MeasurementLoggingServiceTest {

    private static final String GRAM = "Gram";
    private static final long TEN = 10;

    @InjectMocks
    private MeasurementLoggingService service;

    @Mock
    private MeasurementRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Measurement gram = Measurement.builder()
                .description(GRAM)
                .build();
        when(repository.save(gram)).thenReturn(gram);

        Measurement actual = service.save(gram);

        assertThat(actual.getDescription()).isEqualTo(GRAM);
        verify(repository).save(gram);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Measurement gram = Measurement.builder()
                .description(GRAM)
                .build();
        when(repository.findById(TEN)).thenReturn(Optional.of(gram));

        Optional<Measurement> actual = service.findById(TEN);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Measurement> actual = service.findById(TEN);

        assertThat(actual).isEmpty();
    }
}
