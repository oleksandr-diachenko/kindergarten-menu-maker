package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.TestData;
import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.repository.QuantityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
class QuantityLoggingServiceTest {

    @InjectMocks
    private QuantityLoggingService service;

    @Mock
    private QuantityRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Quantity nursery = TestData.nursery();
        when(repository.save(nursery)).thenReturn(nursery);

        Quantity actual = service.save(nursery);

        assertThat(actual.getAmountNet()).isEqualTo(5);
        verify(repository).save(nursery);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Quantity nursery = TestData.nursery();
        when(repository.findById(1L)).thenReturn(of(nursery));

        Optional<Quantity> actual = service.findById(1);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Quantity> actual = service.findById(1);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByNetAndGross() {
        when(repository.existsByAmountNetAndAmountGross(5, 6)).thenReturn(true);

        boolean actual = service.existsByAmountNetAndAmountGross(5, 6);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByNetAndGross() {
        when(repository.existsByAmountNetAndAmountGross(5, 6)).thenReturn(false);

        boolean actual = service.existsByAmountNetAndAmountGross(5, 6);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnQuantityWhenFindByNetAndGrossExist() {
        Quantity nursery = TestData.nursery();
        when(repository.findByAmountNetAndAmountGross(5, 6))
                .thenReturn(Optional.ofNullable(nursery));

        Optional<Quantity> actual = service.findByAmountNetAndAmountGross(5, 6);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getAmountNet()).isEqualTo(5);
        assertThat(actual.get().getAmountGross()).isEqualTo(6);
    }

    @Test
    void shouldReturnEmptyQuantityWhenFindByNetAndGrossNotExist() {
        when(repository.findByAmountNetAndAmountGross(5, 6))
                .thenReturn(empty());

        Optional<Quantity> actual = service.findByAmountNetAndAmountGross(5, 6);

        assertThat(actual).isEmpty();
    }
}
