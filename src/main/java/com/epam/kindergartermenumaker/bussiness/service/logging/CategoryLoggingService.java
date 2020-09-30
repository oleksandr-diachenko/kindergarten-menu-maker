package com.epam.kindergartermenumaker.bussiness.service.logging;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.dao.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryLoggingService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        Category saved = categoryRepository.save(category);
        log.info(saved + " was saved");
        return saved;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = IteratorUtils.toList(categoryRepository.findAll().iterator());
        log.debug(format("Returned all categories: %s", categories));
        return categories;
    }

    @Override
    public boolean existsByName(String name) {
        boolean exists = categoryRepository.existsByName(name);
        log.debug("Is category with name {} exists? {}", name, exists);
        return exists;
    }

    @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> optional = categoryRepository.findByName(name);
        optional.ifPresentOrElse(category -> log.debug(category + " was retrieved"),
                () -> log.debug(format("Category with name %s not found", name)));
        return optional;
    }
}
