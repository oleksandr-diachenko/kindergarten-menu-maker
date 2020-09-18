package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
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
class MeasurementLoggingServiceTest {

    private static final String GRAM = "Gram";

    @InjectMocks
    private MeasurementLoggingService service;

    @Mock
    private MeasurementRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Measurement gram = Measurement.builder()
                .description(GRAM)
                .build();

        service.save(gram);

        verify(repository).save(gram);
    }
}
