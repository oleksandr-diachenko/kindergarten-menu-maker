package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Ingredient;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.epam.kindergartermenumaker.dao.ConstraintViolationExceptionMessage.NOT_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class IngredientRepositoryTest {

    private static final String SALT = "Salt";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void shouldReturnIngredientWhenPersisted() {
        Ingredient salt = Ingredient.builder()
                .name(SALT)
                .build();
        manager.persistAndFlush(salt);

        Optional<Ingredient> actual = ingredientRepository.findById(salt.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(SALT);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Ingredient salt = Ingredient.builder()
                .name(null)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(salt))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }
}