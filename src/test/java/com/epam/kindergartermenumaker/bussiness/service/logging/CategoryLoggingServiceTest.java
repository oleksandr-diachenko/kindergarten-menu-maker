package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryLoggingServiceTest {

    private static final String MAIN_COURSE = "Main course";

    @InjectMocks
    private CategoryLoggingService service;

    @Mock
    private CategoryRepository repository;

    @Test
    void shouldCallSaveRepositoryWhenSaveTriggered() {
        Category mainCourse = Category.builder()
                .name(MAIN_COURSE)
                .build();

        service.save(mainCourse);

        verify(repository).save(mainCourse);
    }

    @Test
    void shouldReturnAllCategoriesWhenFindAllTriggered() {
        Category mainCourse = Category.builder()
                .name(MAIN_COURSE)
                .build();
        when(repository.findAll()).thenReturn(List.of(mainCourse));

        List<Category> actual = service.findAll();

        assertThat(actual).containsExactly(mainCourse);
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByName(MAIN_COURSE)).thenReturn(true);

        boolean actual = service.existsByName(MAIN_COURSE);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByName(MAIN_COURSE)).thenReturn(false);

        boolean actual = service.existsByName(MAIN_COURSE);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnCategoryWhenFindByNameExist() {
        Category mainCourse = Category.builder()
                .name(MAIN_COURSE)
                .build();
        when(repository.findByName(MAIN_COURSE)).thenReturn(Optional.ofNullable(mainCourse));

        Optional<Category> actual = service.findByName(MAIN_COURSE);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().getName()).isEqualTo(MAIN_COURSE);
    }

    @Test
    void shouldReturnEmptyCategoryWhenFindByNameNotExist() {
        when(repository.findByName(MAIN_COURSE)).thenReturn(Optional.empty());

        Optional<Category> actual = service.findByName(MAIN_COURSE);

        assertThat(actual).isEmpty();
    }
}
