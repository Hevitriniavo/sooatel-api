package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.GroupSummarized;
import com.fresh.coding.sooatelapi.services.IngredientsByGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class IngredientsByGroupController {
    private final IngredientsByGroupService ingredientsByGroupService;

    @GetMapping("/ingredient-groups")
    public List<GroupSummarized> getStocksByIngredientsGroup(){
        return ingredientsByGroupService.findStocksByIngredientsGroup();
    }
}
