package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface IngredientService {

    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> findById(long id);

    boolean existsByName(String name);

    Optional<Ingredient> findByName(String name);
}
