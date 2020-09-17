package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class RecipeRepositoryTest {

    private static final String FRIED_POTATOES = "Fried potatoes";
    private static final String FRIED_POTATOES_DESCRIPTION = "Fried potatoes in a skillet";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void shouldReturnQuantityWhenPersisted() {
        Recipe friedPotatoes = Recipe.builder()
                .name(FRIED_POTATOES)
                .description(FRIED_POTATOES_DESCRIPTION)
                .build();
        manager.persistAndFlush(friedPotatoes);

        Optional<Recipe> actual = recipeRepository.findById(friedPotatoes.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(FRIED_POTATOES);
        assertThat(actual.get().getDescription()).isEqualTo(FRIED_POTATOES_DESCRIPTION);
    }
}