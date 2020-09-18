package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Quantity;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface QuantityService {

    void save(Quantity quantity);
}
