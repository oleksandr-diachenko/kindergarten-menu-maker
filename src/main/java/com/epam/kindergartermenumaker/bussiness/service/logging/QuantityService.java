package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Quantity;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface QuantityService {

    Quantity save(Quantity quantity);

    Optional<Quantity> findById(long id);

    boolean existsByAmountNetAndAmountGross(double amountNet, double amountGross);

    Optional<Quantity> findByAmountNetAndAmountGross(double amountNet, double amountGross);
}
