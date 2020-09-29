package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Measurement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

    boolean existsByDescription(String description);

    Optional<Measurement> findByDescription(String description);
}
