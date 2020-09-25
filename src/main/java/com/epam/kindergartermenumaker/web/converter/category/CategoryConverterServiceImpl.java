package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Service
@RequiredArgsConstructor
public class CategoryConverterServiceImpl implements CategoryConverterService {

    private final CategoryService categoryService;
    private final Converter<Category, CategoryDTO> categoryToDtoConverter;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryService.findAll().stream()
                .map(categoryToDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
