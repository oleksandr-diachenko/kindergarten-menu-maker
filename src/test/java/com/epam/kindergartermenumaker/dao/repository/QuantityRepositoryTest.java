package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class QuantityRepositoryTest {

    private static final int TWO = 2;
    private static final int THREE = 3;

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
    void shouldReturnTrueWhenQuantityExistsByNetAndGross() {
        Quantity two = Quantity.builder()
                .amountNet(TWO)
                .amountGross(THREE)
                .build();
        manager.persistAndFlush(two);

        boolean exists = quantityRepository.existsByAmountNetAndAmountGross(TWO, THREE);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenQuantityNotExistsByNetAndGross() {
        Quantity two = Quantity.builder()
                .amountNet(TWO)
                .amountGross(THREE)
                .build();
        manager.persistAndFlush(two);

        boolean exists = quantityRepository.existsByAmountNetAndAmountGross(-1, -2);

        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnQuantityByNetAndGrossWhenPersisted() {
        Quantity two = Quantity.builder()
                .amountNet(TWO)
                .amountGross(THREE)
                .build();
        manager.persistAndFlush(two);

        Optional<Quantity> actual = quantityRepository.findByAmountNetAndAmountGross(TWO, THREE);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getAmountNet()).isEqualTo(TWO);
        assertThat(actual.get().getAmountGross()).isEqualTo(THREE);
    }

    @Test
    void shouldReturnEmptyQuantityByNetAndGrossWhenPersisted() {
        Quantity two = Quantity.builder()
                .amountNet(TWO)
                .amountGross(THREE)
                .build();
        manager.persistAndFlush(two);

        Optional<Quantity> actual = quantityRepository.findByAmountNetAndAmountGross(-1, -2);

        assertThat(actual).isEmpty();
    }
}