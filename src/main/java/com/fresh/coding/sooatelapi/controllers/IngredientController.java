package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.services.ingredients.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientSummarized createIngredient(@RequestBody @Valid CreateIngredient toCreate){
        return ingredientService.create(toCreate);
    }
}
