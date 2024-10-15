package com.fresh.coding.sooatelapi.controllers.ingredients;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuWithIngredientsDTO;
import com.fresh.coding.sooatelapi.services.menus.MenuIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/menu-ingredients")
@RequiredArgsConstructor
public class MenuIngredientController {

    private final MenuIngredientService menuIngredientService;

    @GetMapping("/menu/{menuId}")
    public MenuWithIngredientsDTO findByMenuId(@PathVariable Long menuId) {
        return menuIngredientService.findIngredientsByMenuId(menuId);
    }
}
