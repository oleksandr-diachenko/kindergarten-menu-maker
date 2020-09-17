package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
