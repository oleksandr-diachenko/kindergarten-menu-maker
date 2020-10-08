package com.epam.kindergartermenumaker.web.adapter;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
public interface RecipeServiceAdapter {

    void save(RecipeForm recipeForm);

    void update(RecipeForm recipeForm);

    RecipeForm findByRecipeName(String name);
}
