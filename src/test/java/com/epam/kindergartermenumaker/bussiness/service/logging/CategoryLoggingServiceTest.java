package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
}
