package com.epam.kindergartermenumaker.web.converter.category;

import com.epam.kindergartermenumaker.dao.entity.Category;
import com.epam.kindergartermenumaker.web.converter.recipe.RecipeDTO;
import lombok.*;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/25/2020
 **/
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CategoryDTO {

    private Category category;
    private List<RecipeDTO> recipes;
}
