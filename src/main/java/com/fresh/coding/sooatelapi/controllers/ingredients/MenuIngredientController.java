package com.fresh.coding.sooatelapi.controllers.ingredients;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuWithIngredientsDTO;
import com.fresh.coding.sooatelapi.services.menus.MenuIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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


    @DeleteMapping("/ingredients/{menuIngredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuIngredient(@PathVariable Long menuIngredientId) {
        menuIngredientService.deleteMenuIngredientById(menuIngredientId);
    }

    @DeleteMapping("/menu/{menuId}/ingredient/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuIngredientByMenuAndIngredient(
            @PathVariable Long menuId,
            @PathVariable Long ingredientId) {
        menuIngredientService.deleteMenuIngredientByMenuIdAndIngredientId(menuId, ingredientId);
    }
}
