package com.epam.kindergartermenumaker.web.controller;

import com.epam.kindergartermenumaker.web.converter.category.CategoryConverterService;
import com.epam.kindergartermenumaker.web.converter.category.CategoryDTO;
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

    private final CategoryConverterService categoryConverterService;

    @GetMapping
    public String getAllCategories(Model model) {
        List<CategoryDTO> categoryDTOS = categoryConverterService.getAllCategories();
        model.addAttribute("categories", categoryDTOS);
        return "recipes";
    }
}
