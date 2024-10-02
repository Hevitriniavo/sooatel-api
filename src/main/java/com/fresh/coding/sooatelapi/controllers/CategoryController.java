package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.services.categories.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CategorySummarized save(@Valid @RequestBody  SaveCategory toSave){
        return categoryService.save(toSave);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CategorySummarized> getAllCategories(){
        return categoryService.findAllCategories();
    }
}
