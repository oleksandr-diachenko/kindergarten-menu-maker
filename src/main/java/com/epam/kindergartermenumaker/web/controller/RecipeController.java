package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.bussiness.service.converter.RecipeConverterService;
import com.epam.kindergartermenumaker.bussiness.service.converter.RecipeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeConverterService recipeConverterService;

    @GetMapping
    public String getAllRecipes(Model model) {
        List<RecipeDTO> recipeDTOS = recipeConverterService.getAllRecipes();
        model.addAttribute("recipes", recipeDTOS);
        return "recipes";
    }
}
