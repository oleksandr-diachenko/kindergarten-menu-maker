package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface RecipeService {

    void save(Recipe recipe);
}