package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
public interface CategoryService {

    Category save(Category category);

    List<Category> findAll();

    boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
