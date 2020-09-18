package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Measurement;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface MeasurementService {

    void save(Measurement measurement);
}
