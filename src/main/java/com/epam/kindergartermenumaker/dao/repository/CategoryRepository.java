package com.epam.kindergartermenumaker.dao.repository;

import com.epam.kindergartermenumaker.dao.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
