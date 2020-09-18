package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.repository.QuantityRepository;
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
class QuantityLoggingServiceTest {

    private static final int TEN = 10;

    @InjectMocks
    private QuantityLoggingService service;

    @Mock
    private QuantityRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Quantity ten = Quantity.builder()
                .amount(TEN)
                .build();

        service.save(ten);

        verify(repository).save(ten);
    }
}
