package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface RecipeIngredientService {

    void save(RecipeIngredient recipeIngredient);

    Optional<RecipeIngredient> findById(long id);
}
