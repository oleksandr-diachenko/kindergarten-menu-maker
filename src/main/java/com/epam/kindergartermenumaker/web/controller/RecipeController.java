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

    private final CategoryConverterService categoryConverterService;
    private final RecipeServiceAdapter recipeServiceAdapter;
    private final Parser<RecipeForm> parser;

    @GetMapping
    public String getAllCategories(Model model) {
        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllNonEmptyCategories();
        model.addAttribute("categories", categoryDTOS);
        return "recipes";
    }

    @GetMapping(value = "update")
    public String getUpdateRecipeForm(Model model, @RequestParam(defaultValue = "1") int ingredientsCount) {
        List<IngredientForm> ingredientForms = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredientForms.add(new IngredientForm());
        }
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setIngredients(ingredientForms);
        model.addAttribute("recipeForm", recipeForm);
        return "update-recipe";
    }

    @PostMapping
    public String updateRecipe(@ModelAttribute RecipeForm recipeForm) {
        recipeServiceAdapter.save(recipeForm);
        return "redirect:/recipes";
    }

    @PostMapping("load")
    public String loadRecipe(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        model.addAttribute("recipeForm", parser.parse(file.getInputStream()));
        return "update-recipe";
    }
}
