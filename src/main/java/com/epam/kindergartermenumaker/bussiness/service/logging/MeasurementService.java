package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface MeasurementService {

    Measurement save(Measurement measurement);

    Optional<Measurement> findById(long id);

    boolean existsByDescription(String description);

    Optional<Measurement> findByDescription(String description);
}
