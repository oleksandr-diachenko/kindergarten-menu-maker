package com.epam.kindergartermenumaker.bussiness.service.converter;

import com.epam.kindergartermenumaker.dao.entity.Recipe;
import com.epam.kindergartermenumaker.dao.entity.RecipeIngredient;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class RecipeDTO {

    private final Recipe recipe;
    private final List<RecipeIngredient> ingredients;
}
