package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.epam.kindergartermenumaker.dao.ConstraintViolationExceptionMessage.NOT_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class MeasurementRepositoryTest {

    private static final String GRAM = "Gram";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Test
    void shouldReturnMeasurementWhenPersisted() {
        Measurement gram = Measurement.builder()
                .description(GRAM)
                .build();
        manager.persistAndFlush(gram);

        Optional<Measurement> actual = measurementRepository.findById(gram.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getDescription()).isEqualTo(GRAM);
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Measurement measurement = Measurement.builder()
                .description(null)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(measurement))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }
}