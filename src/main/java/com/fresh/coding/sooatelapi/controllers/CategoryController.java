package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.services.categories.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategorySummarized save(@Valid @RequestBody  SaveCategory toSave){
        return categoryService.save(toSave);
    }
}
