package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuIngredientDto;
import com.fresh.coding.sooatelapi.services.menus.AddIngredientsToMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class AddIngredientsMenuController {

    private final AddIngredientsToMenuService addIngredientsToMenuService;

    @PostMapping("/add-ingredients")
    public void addIngredientsToMenu(@Valid @RequestBody MenuIngredientDto menuIngredientDto) {
        addIngredientsToMenuService.addIngredientsToMenu(menuIngredientDto);
    }
}
