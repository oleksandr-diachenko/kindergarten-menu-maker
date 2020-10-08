package com.epam.kindergartermenumaker.web.adapter;

import lombok.*;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/29/2020
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class IngredientForm {

    private long recipeIngredientId;
    private String ingredientName;
    private double fat;
    private double protein;
    private double carbohydrate;
    private double nurseryNetAmount;
    private double nurseryGrossAmount;
    private double kindergartenNetAmount;
    private double kindergartenGrossAmount;
    private String measurementDescription = "Грам";
}
