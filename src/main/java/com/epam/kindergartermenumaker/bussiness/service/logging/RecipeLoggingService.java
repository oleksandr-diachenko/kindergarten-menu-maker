package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
