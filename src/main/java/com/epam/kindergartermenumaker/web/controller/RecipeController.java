package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.bussiness.service.parser.Parser;
import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeServiceAdapter;
import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private static final String RECIPE_FORM = "recipeForm";
    private static final String UPDATE_RECIPE = "update-recipe";
    private static final String RECIPES = "recipes";
    private static final String REDIRECT_RECIPES = "redirect:/recipes";

    private final CategoryConverterService categoryConverterService;
    private final RecipeServiceAdapter recipeServiceAdapter;
    private final Parser<RecipeForm> parser;

    @GetMapping
    public String getAllCategories(Model model, @RequestParam(defaultValue = "") String filter) {
        List<CategoryDTO> categoryDTOS;
        if (filter.isEmpty()) {
            categoryDTOS = categoryConverterService.getAllNonEmptyCategories();
        } else {
            categoryDTOS = categoryConverterService.getCategoriesByFilter(filter);
        }
        model.addAttribute("categories", categoryDTOS);
        return RECIPES;
    }

    @GetMapping(value = "create")
    public String getCreateRecipeForm(Model model,
                                      @RequestParam(defaultValue = "1") int ingredientsCount) {
        List<IngredientForm> ingredientForms = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredientForms.add(new IngredientForm());
        }
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setIngredients(ingredientForms);
        model.addAttribute(RECIPE_FORM, recipeForm);
        return UPDATE_RECIPE;
    }

    @GetMapping(value = "update")
    public String getUpdateRecipeForm(Model model, @RequestParam String name) {
        model.addAttribute(RECIPE_FORM, recipeServiceAdapter.findByRecipeName(name));
        return UPDATE_RECIPE;
    }

    @PostMapping
    public String updateRecipe(@ModelAttribute RecipeForm recipeForm) {
        if (recipeForm.getRecipeId() > 0) {
            recipeServiceAdapter.update(recipeForm);
        } else {
            recipeServiceAdapter.save(recipeForm);
        }
        return REDIRECT_RECIPES;
    }

    @PostMapping("load")
    public String loadRecipe(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        model.addAttribute(RECIPE_FORM, parser.parse(file.getInputStream()));
        return UPDATE_RECIPE;
    }
}
