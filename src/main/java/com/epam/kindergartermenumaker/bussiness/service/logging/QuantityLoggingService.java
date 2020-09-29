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
    public Quantity save(Quantity quantity) {
        Quantity saved = quantityRepository.save(quantity);
        log.info(saved + " was saved");
        return saved;
    }

    @Override
    public Optional<Quantity> findById(long id) {
        Optional<Quantity> optional = quantityRepository.findById(id);
        optional.ifPresentOrElse(quantity -> log.debug(quantity + " was retrieved"),
                () -> log.debug(format("Measurement quantity with id %s not found", id)));
        return optional;
    }

    @Override
    public boolean existsByAmountNetAndAmountGross(double amountNet, double amountGross) {
        boolean exists = quantityRepository.existsByAmountNetAndAmountGross(amountNet, amountGross);
        log.debug("Is quantity with net {} and gross {} exists? {}", amountNet, amountGross, exists);
        return exists;
    }

    @Override
    public Optional<Quantity> findByAmountNetAndAmountGross(double amountNet, double amountGross) {
        Optional<Quantity> optional = quantityRepository.findByAmountNetAndAmountGross(amountNet, amountGross);
        optional.ifPresentOrElse(quantity -> log.debug(quantity + " was retrieved"),
                () -> log.debug(format("Quantity with net %s and gross %s not found", amountNet, amountGross)));
        return optional;
    }
}
