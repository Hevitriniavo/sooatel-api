package com.fresh.coding.sooatelapi.controllers;


import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.IngredientSearch;
import com.fresh.coding.sooatelapi.services.ingredients.SearchIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class SearchIngredientController {
    private final SearchIngredientService searchIngredientService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<IngredientSummarizedWithUnitName>> getAllIngredients(
            @ModelAttribute IngredientSearch ingredientSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return searchIngredientService.findAllIngredient(ingredientSearch, page, size);
    }
}
