package com.epam.kindergartermenumaker.web.adapter;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecipeForm {

    private long recipeId;
    private String categoryName;
    private String recipeName;
    private String recipeDescription;
    private List<IngredientForm> ingredients = new ArrayList<>();
}
