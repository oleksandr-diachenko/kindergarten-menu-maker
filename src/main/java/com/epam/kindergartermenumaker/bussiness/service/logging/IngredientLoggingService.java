package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.repository.IngredientRepository;
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
public class IngredientLoggingService implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient save(Ingredient ingredient) {
        Ingredient saved = ingredientRepository.save(ingredient);
        log.info(saved + " was saved");
        return saved;
    }

    @Override
    public Optional<Ingredient> findById(long id) {
        Optional<Ingredient> optional = ingredientRepository.findById(id);
        optional.ifPresentOrElse(ingredient -> log.debug(ingredient + " was retrieved"),
                () -> log.debug(format("Ingredient with id %s not found", id)));
        return optional;
    }

    @Override
    public boolean existsByName(String name) {
        boolean exists = ingredientRepository.existsByName(name);
        log.debug("Is ingredient with name {} exists? {}", name, exists);
        return exists;
    }

    @Override
    public Optional<Ingredient> findByName(String name) {
        Optional<Ingredient> optional = ingredientRepository.findByName(name);
        optional.ifPresentOrElse(ingredient -> log.debug(ingredient + " was retrieved"),
                () -> log.debug(format("Ingredient with name %s not found", name)));
        return optional;
    }
}
