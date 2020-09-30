package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.bussiness.service.logging.CategoryService;
import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.*;

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
    public List<CategoryDTO> getAllNonEmptyCategories() {
        return categoryService.findAll().stream()
                .map(categoryToDtoConverter::convert)
                .filter(categoryDTO -> isNotEmpty(categoryDTO.getRecipes()))
                .collect(Collectors.toList());
    }
}
