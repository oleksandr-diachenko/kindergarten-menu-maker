package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import com.epam.kindergartermenumaker.dao.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
