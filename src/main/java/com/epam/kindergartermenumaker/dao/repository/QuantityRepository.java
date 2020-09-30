package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Quantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Repository
public interface QuantityRepository extends CrudRepository<Quantity, Long> {

    boolean existsByAmountNetAndAmountGross(double amountNet, double amountGross);

    Optional<Quantity> findByAmountNetAndAmountGross(double amountNet, double amountGross);
}
