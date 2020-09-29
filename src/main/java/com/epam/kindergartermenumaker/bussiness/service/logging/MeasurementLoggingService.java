package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
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
public class MeasurementLoggingService implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Override
    public Measurement save(Measurement measurement) {
        Measurement saved = measurementRepository.save(measurement);
        log.info(saved + " was saved");
        return saved;
    }

    @Override
    public Optional<Measurement> findById(long id) {
        Optional<Measurement> optional = measurementRepository.findById(id);
        optional.ifPresentOrElse(measurement -> log.debug(measurement + " was retrieved"),
                () -> log.debug(format("Measurement unit with id %s not found", id)));
        return optional;
    }

    @Override
    public boolean existsByDescription(String description) {
        boolean exists = measurementRepository.existsByDescription(description);
        log.debug("Is measurement with description {} exists? {}", description, exists);
        return exists;
    }

    @Override
    public Optional<Measurement> findByDescription(String description) {
        Optional<Measurement> optional = measurementRepository.findByDescription(description);
        optional.ifPresentOrElse(measurement -> log.debug(measurement + " was retrieved"),
                () -> log.debug(format("Measurement with description %s not found", description)));
        return optional;
    }
}
