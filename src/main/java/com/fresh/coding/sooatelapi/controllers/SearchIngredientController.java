package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
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
            @RequestParam(required = false, name = "ingredient_name") String ingredientName,
            @RequestParam(required = false, name = "unit_name") String unitName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return searchIngredientService.findAllIngredient(ingredientName, unitName, page, size);
    }

}
