package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;
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
    public Recipe save(Recipe recipe) {
        Recipe saved = recipeRepository.save(recipe);
        log.info(saved + " was saved");
        return saved;
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

    @Override
    public List<Recipe> findByCategory(Category category) {
        List<Recipe> recipes = recipeRepository.findByCategory(category);
        log.debug(format("For category: %s returned all recipes: %s", category, recipes));
        return recipes;
    }

    @Override
    public boolean existsByName(String name) {
        boolean exists = recipeRepository.existsByName(name);
        log.debug("Is recipe with name {} exists? {}", name, exists);
        return exists;
    }

    @Override
    public Optional<Recipe> findByName(String name) {
        Optional<Recipe> optional = recipeRepository.findByName(name);
        optional.ifPresentOrElse(recipe -> log.debug(recipe + " was retrieved"),
                () -> log.debug(format("Recipe with name %s not found", name)));
        return optional;
    }

    @Override
    public List<Recipe> findByNameContainsIgnoreCase(String filter) {
        List<Recipe> recipes = recipeRepository.findByNameContainsIgnoreCase(filter);
        log.debug(format("Returned by filter: %s all recipes: %s", filter, recipes));
        return recipes;
    }
}
