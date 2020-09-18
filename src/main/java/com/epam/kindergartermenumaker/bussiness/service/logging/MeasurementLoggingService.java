package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import com.epam.kindergartermenumaker.dao.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
        log.info(measurement + " was saved");
    }
}
