package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import com.epam.kindergartermenumaker.dao.repository.IngredientRepository;
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
public class IngredientLoggingService implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        log.info(ingredient + " was saved");
    }
}
