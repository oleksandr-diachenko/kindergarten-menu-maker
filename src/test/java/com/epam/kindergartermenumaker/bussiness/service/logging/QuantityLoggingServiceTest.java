package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.repository.QuantityRepository;
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
class QuantityLoggingServiceTest {

    private static final int TEN = 10;
    private static final long FIVE = 5;

    @InjectMocks
    private QuantityLoggingService service;

    @Mock
    private QuantityRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Quantity ten = Quantity.builder()
                .amountNet(TEN)
                .build();
        when(repository.save(ten)).thenReturn(ten);

        Quantity actual = service.save(ten);

        assertThat(actual.getAmountNet()).isEqualTo(TEN);
        verify(repository).save(ten);
    }

    @Test
    void shouldReturnNonEmptyWhenFindByIdTriggered() {
        Quantity ten = Quantity.builder()
                .amountNet(TEN)
                .build();
        when(repository.findById(FIVE)).thenReturn(Optional.of(ten));

        Optional<Quantity> actual = service.findById(FIVE);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdTriggered() {
        Optional<Quantity> actual = service.findById(FIVE);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByNetAndGross() {
        when(repository.existsByAmountNetAndAmountGross(FIVE, TEN)).thenReturn(true);

        boolean actual = service.existsByAmountNetAndAmountGross(FIVE, TEN);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByNetAndGross() {
        when(repository.existsByAmountNetAndAmountGross(FIVE, TEN)).thenReturn(false);

        boolean actual = service.existsByAmountNetAndAmountGross(FIVE, TEN);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnQuantityWhenFindByNetAndGrossExist() {
        Quantity quantity = Quantity.builder()
                .amountNet(FIVE)
                .amountGross(TEN)
                .build();
        when(repository.findByAmountNetAndAmountGross(FIVE, TEN)).thenReturn(Optional.ofNullable(quantity));

        Optional<Quantity> actual = service.findByAmountNetAndAmountGross(FIVE, TEN);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getAmountNet()).isEqualTo(FIVE);
        assertThat(actual.get().getAmountGross()).isEqualTo(TEN);
    }

    @Test
    void shouldReturnEmptyQuantityWhenFindByNetAndGrossNotExist() {
        when(repository.findByAmountNetAndAmountGross(FIVE, TEN)).thenReturn(Optional.empty());

        Optional<Quantity> actual = service.findByAmountNetAndAmountGross(FIVE, TEN);

        assertThat(actual).isEmpty();
    }
}
