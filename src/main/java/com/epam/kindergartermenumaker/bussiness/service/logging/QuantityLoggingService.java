package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import com.epam.kindergartermenumaker.dao.repository.QuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class QuantityLoggingService implements QuantityService {

    private final QuantityRepository quantityRepository;

    @Override
    public void save(Quantity quantity) {
        quantityRepository.save(quantity);
        log.info(quantity + " was saved");
    }

    @Override
    public Optional<Quantity> findById(long id) {
        Optional<Quantity> optional = quantityRepository.findById(id);
        optional.ifPresentOrElse(quantity -> log.debug(quantity + " was retrieved"),
                () -> log.debug(format("Measurement quantity with id %s not found", id)));
        return optional;
    }
}
