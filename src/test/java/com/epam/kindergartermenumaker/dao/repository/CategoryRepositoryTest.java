package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Category;
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
 * @since : 9/25/2020
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Tag("spring")
class CategoryRepositoryTest {

    private static final String MAIN_COURSE = "Main course";

    @Autowired
    private TestEntityManager manager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldReturnCategoryWhenPersisted() {
        Category category = Category.builder()
                .name(MAIN_COURSE)
                .build();
        manager.persistAndFlush(category);

        Optional<Category> actual = categoryRepository.findById(category.getId());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(MAIN_COURSE);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Category category = Category.builder()
                .name(null)
                .build();

        assertThatThrownBy(() -> manager.persistAndFlush(category))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(NOT_NULL.getMessage());
    }
}