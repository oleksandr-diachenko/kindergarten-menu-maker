package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
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
public class RecipeLoggingService implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
        log.info(recipe + " was saved");
    }

    @Override
    public Optional<Recipe> findById(long id) {
        Optional<Recipe> optional = recipeRepository.findById(id);
        optional.ifPresentOrElse(recipe -> log.debug(recipe + " was retrieved"),
                () -> log.debug(format("Recipe with id %s not found", id)));
        return optional;
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = IteratorUtils.toList(recipeRepository.findAll().iterator());
        log.debug(format("Returned all recipes: %s", recipes));
        return recipes;
    }
}
