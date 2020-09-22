package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeIngredientLoggingService implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public void save(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.save(recipeIngredient);
        log.info(recipeIngredient + " was saved");
    }

    @Override
    public Optional<RecipeIngredient> findById(long id) {
        Optional<RecipeIngredient> optional = recipeIngredientRepository.findById(id);
        optional.ifPresentOrElse(recipeIngredient -> log.debug(recipeIngredient + " was retrieved"),
                () -> log.debug(format("Recipe ingredient with id %s not found", id)));
        return optional;
    }

    @Override
    public List<RecipeIngredient> findAll() {
        List<RecipeIngredient> recipeIngredients = IteratorUtils.toList(recipeIngredientRepository.findAll().iterator());
        log.debug(format("Returned all recipe ingredients: %s", recipeIngredients));
        return recipeIngredients;
    }

    @Override
    public List<RecipeIngredient> findByRecipe(Recipe recipe) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByRecipe(recipe);
        log.debug(format("For recipe: %s returned all recipe ingredients: %s", recipe, recipeIngredients));
        return recipeIngredients;
    }
}
