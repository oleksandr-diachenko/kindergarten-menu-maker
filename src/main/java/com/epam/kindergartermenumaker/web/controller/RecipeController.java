package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeServiceAdapter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final CategoryConverterService categoryConverterService;
    private final RecipeServiceAdapter recipeServiceAdapter;

    @GetMapping
    public String getAllCategories(Model model) {
        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllCategories();
        model.addAttribute("categories", categoryDTOS);
        return "recipes";
    }

    @GetMapping(value = "create-recipe")
    public String getCreateRecipeForm(Model model, @RequestParam(defaultValue = "1") int ingredientsCount)  {
        List<IngredientForm> ingredientForms = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredientForms.add(new IngredientForm());
        }
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setIngredients(ingredientForms);
        model.addAttribute("recipeForm", recipeForm);
        return "create-recipe";
    }

    @PostMapping
    public String createRecipe(@ModelAttribute RecipeForm recipeForm)  {
        recipeServiceAdapter.save(recipeForm);
        return "redirect:/recipes";
    }
}
