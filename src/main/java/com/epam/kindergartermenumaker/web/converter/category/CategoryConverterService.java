package com.epam.kindergartermenumaker.web.converter.category;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
public interface CategoryConverterService {

    List<CategoryDTO> getAllNonEmptyCategories();

    List<CategoryDTO> getCategoriesByFilter(String filter);
}
