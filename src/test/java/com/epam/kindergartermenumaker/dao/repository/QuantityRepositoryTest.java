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
}