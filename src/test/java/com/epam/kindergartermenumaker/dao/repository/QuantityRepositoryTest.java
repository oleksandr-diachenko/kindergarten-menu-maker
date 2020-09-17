package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.epam.kindergartermenumaker.ConstraintViolationExceptionMessage.GREATER_THEN_ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class QuantityRepositoryTest {

    private static final int TWO = 2;

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private QuantityRepository quantityRepository;

    @Test
    void shouldReturnQuantityWhenPersisted() {
        Quantity two = Quantity.builder()
                .amount(TWO)
                .build();
        manager.persistAndFlush(two);

        Optional<Quantity> actual = quantityRepository.findById(two.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getAmount()).isEqualTo(TWO);
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        Quantity quantity = Quantity.builder()
                .amount(0)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(quantity))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(GREATER_THEN_ZERO.getMessage());
    }
}