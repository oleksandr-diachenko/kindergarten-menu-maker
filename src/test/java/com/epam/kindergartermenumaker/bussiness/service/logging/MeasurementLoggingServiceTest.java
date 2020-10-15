package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.epam.kindergartermenumaker.TestData.GRAM;
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
class MeasurementLoggingServiceTest {

    @InjectMocks
    private MeasurementLoggingService service;

    @Mock
    private MeasurementRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Measurement gram = TestData.measurement();
        when(repository.save(gram)).thenReturn(gram);

        Measurement actual = service.save(gram);

        assertThat(actual.getDescription()).isEqualTo(GRAM);
        verify(repository).save(gram);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Measurement gram = TestData.measurement();
        when(repository.findById(1L)).thenReturn(of(gram));

        Optional<Measurement> actual = service.findById(1);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Measurement> actual = service.findById(1);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByDescription(GRAM)).thenReturn(true);

        boolean actual = service.existsByDescription(GRAM);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByDescription(GRAM)).thenReturn(false);

        boolean actual = service.existsByDescription(GRAM);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnMeasurementWhenFindByNameExist() {
        Measurement gram = TestData.measurement();
        when(repository.findByDescription(GRAM)).thenReturn(of(gram));

        Optional<Measurement> actual = service.findByDescription(GRAM);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getDescription()).isEqualTo(GRAM);
    }

    @Test
    void shouldReturnEmptyMeasurementWhenFindByNameNotExist() {
        when(repository.findByDescription(GRAM)).thenReturn(empty());

        Optional<Measurement> actual = service.findByDescription(GRAM);

        assertThat(actual).isEmpty();
    }
}
