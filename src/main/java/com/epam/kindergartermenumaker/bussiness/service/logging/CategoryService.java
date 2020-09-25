package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
public interface CategoryService {

    Category save(Category category);

    List<Category> findAll();
}
