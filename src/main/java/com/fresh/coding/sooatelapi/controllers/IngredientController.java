package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.dtos.ingredients.UpdateIngredient;
import com.fresh.coding.sooatelapi.services.ingredients.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public IngredientSummarized updateIngredient(@RequestBody @Valid UpdateIngredient toUpdate){
        return ingredientService.update(toUpdate);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientSummarized> getAllIngredients(){
        return ingredientService.findAllIngredients();
    }
}
