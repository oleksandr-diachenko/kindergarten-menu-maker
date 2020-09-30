package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    boolean existsByName(String name);

    Optional<Ingredient> findByName(String name);
}
