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

import static com.epam.kindergartermenumaker.dao.ConstraintViolationExceptionMessage.GREATER_THEN_ZERO;
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
                .amountNet(TWO)
                .amountGross(TWO)
                .build();
        manager.persistAndFlush(two);

        Optional<Quantity> actual = quantityRepository.findById(two.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getAmountNet()).isEqualTo(TWO);
        assertThat(actual.get().getAmountGross()).isEqualTo(TWO);
    }

    @Test
    void shouldThrowExceptionWhenQuantityNetIsZero() {
        Quantity quantity = Quantity.builder()
                .amountNet(0)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(quantity))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(GREATER_THEN_ZERO.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityGrossIsZero() {
        Quantity quantity = Quantity.builder()
                .amountNet(TWO)
                .amountGross(0)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(quantity))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(GREATER_THEN_ZERO.getMessage());
    }
}