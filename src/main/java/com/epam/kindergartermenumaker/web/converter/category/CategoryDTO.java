package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class CategoryDTO {

    private final Category category;
    private final List<RecipeDTO> recipes;
}
