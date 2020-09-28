package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.entity.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface RecipeService {

    Recipe save(Recipe recipe);

    Optional<Recipe> findById(long id);

    List<Recipe> findAll();

    List<Recipe> findByCategory(Category category);
}
