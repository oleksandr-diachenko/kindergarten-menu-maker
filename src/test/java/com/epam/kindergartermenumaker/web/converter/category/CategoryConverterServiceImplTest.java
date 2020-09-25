package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/28/2020
 **/
@ExtendWith(MockitoExtension.class)
class CategoryConverterServiceImplTest {

    private static final String MAIN_SOURCE = "Main source";

    @InjectMocks
    private CategoryConverterServiceImpl categoryConverterService;

    @Mock
    private CategoryService categoryService;
    @Mock
    private Converter<Category, CategoryDTO> categoryToCategoryDTOConverter;

    @Test
    void shouldReturnListOfCategoryDTOs() {
        Category mainSource = Category.builder().name(MAIN_SOURCE).build();
        when(categoryService.findAll()).thenReturn(List.of(mainSource));
        CategoryDTO categoryDTO = CategoryDTO.builder().category(mainSource).build();
        when(categoryToCategoryDTOConverter.convert(mainSource)).thenReturn(categoryDTO);

        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllCategories();

        assertThat(categoryDTOS).containsExactly(categoryDTO);
    }
}